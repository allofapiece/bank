package by.bsuir.bank.service.wrapper;

import by.bsuir.bank.bundle.BundleNamesStore;
import by.bsuir.bank.controller.Page;
import by.bsuir.bank.controller.ResponseInfo;
import by.bsuir.bank.controller.Router;
import by.bsuir.bank.entity.User;
import by.bsuir.bank.service.validator.Errors;
//import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpWrapper {
    private ResponseInfo responseInfo;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> jsonResponse;
    private HttpSession session;
    private Map<String, Object> reqAttrs;
    private Map<String, String> reqParams;
    private Map<String, Object> sessionAttrs;
    private Errors errors;
    private Router router;

    public HttpWrapper() {
        responseInfo = new ResponseInfo();
        reqAttrs = new HashMap<>();
        reqParams = new HashMap<>();
        sessionAttrs = new HashMap<>();
        errors = new Errors();
        router = Router.getInstance();
    }

    public HttpWrapper(HttpServletRequest request) {
        this();
        setRequest(request);
    }

    public HttpWrapper(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        this();
        setRequest(request);
        setResponse(response);
        setSession(session);
    }

    public HttpWrapper(HttpServletRequest request, HttpServletResponse response) {
        this();

        setRequest(request);
        setResponse(response);
        setSession(request.getSession());

        switchLanguage();
    }

    public void go(RequestDispatcher requestDispatcher)
            throws ServletException, IOException {

        eject();

        if (responseInfo.isUpdated()) {
            response.sendRedirect(responseInfo.getPage().getPath());
        } else {
            requestDispatcher.forward(request, response);
        }
    }

    public void goAjax() throws IOException {
        jsonResponse = new HashMap<>();
        jsonResponse = ejectJson();
        //String json = new Gson().toJson(jsonResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(json);
    }

    public void go() throws ServletException, IOException {
        if (isAjax()) {
            goAjax();
        } else {
            if (getHttpError() != 0) {
                response.sendError(responseInfo.getHttpError());
                return;
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(responseInfo.getPage().getPath());
            go(requestDispatcher);
        }
    }

    public void fill(HttpServletRequest req) {
        fill(req, req.getSession());
    }

    public void fill(
            HttpServletRequest req,
            HttpSession session
    ) {
        this.request = req;
        this.session = session;

        fillRequestAttributes(req);
        fillRequestParameters(req);
        fillSessionAttributes(session);
    }

    public void fillRequestAttributes(HttpServletRequest request) {
        Enumeration names = request.getAttributeNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            reqAttrs.put(name, request.getAttribute(name));
            request.removeAttribute(name);
        }
    }

    public void fillRequestParameters(HttpServletRequest request) {
        Enumeration names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            reqParams.put(name, request.getParameter(name));
            request.removeAttribute(name);
        }
    }

    public void fillSessionAttributes(HttpSession session) {
        Enumeration names = session.getAttributeNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            sessionAttrs.put(name, session.getAttribute(name));
        }
    }

    public void eject() {
        ejectRequestAttributes();
        ejectRequestParameters();
        ejectSessionAttributes();
        ejectResponseInfo();
        ejectErrors();
    }

    public HttpServletRequest ejectRequestAttributes() {
        if (isUpdated()) {
            for (Map.Entry<String, Object> attr : reqAttrs.entrySet()) {
                this.session.setAttribute(attr.getKey(), attr.getValue());
            }
        } else {
            for (Map.Entry<String, Object> attr : reqAttrs.entrySet()) {
                this.request.setAttribute(attr.getKey(), attr.getValue());
            }
        }

        return this.request;
    }

    public HttpServletRequest ejectRequestParameters() {
        if (isUpdated()) {
            for (Map.Entry<String, String> attr : reqParams.entrySet()) {
                this.session.setAttribute(attr.getKey(), attr.getValue());
            }
        } else {
            for (Map.Entry<String, String> attr : reqParams.entrySet()) {
                this.request.setAttribute(attr.getKey(), attr.getValue());
            }
        }

        return this.request;
    }

    public HttpSession ejectSessionAttributes() {
        for (Map.Entry<String, Object> attr : sessionAttrs.entrySet()) {
            this.session.setAttribute(attr.getKey(), attr.getValue());
        }

        return this.session;
    }

    public HttpServletRequest ejectResponseInfo() {
        if (isUpdated()) {
            this.session.setAttribute("title", getTitle());
        } else {
            this.request.setAttribute("title", getTitle());
        }

        return this.request;
    }

    public HttpServletRequest ejectErrors() {
        if (getErrors().hasErrors()) {
            if (isUpdated()) {
                this.session.setAttribute("errors", getAllErrors());
                sessionAttrs.put("errors", getAllErrors());
            } else {
                this.request.setAttribute("errors", getAllErrors());
                reqAttrs.put("errors", getAllErrors());
            }
        }

        return this.request;
    }

    public Map<String, Object> ejectJson() {
        ResourceBundle bundle = ResourceBundle.getBundle(BundleNamesStore.LOCALIZATION_BUNDLE, request.getLocale());

        if (getErrors().hasErrors()) {
            List<String> ajaxErrors = new ArrayList<>();

            for (Map.Entry entry : getErrors().getAllErrors().entrySet()) {
                for (String error : (ArrayList<String>) entry.getValue()) {
                    ajaxErrors.add(bundle.getString(entry.getKey() + "." + error + ".message"));
                }
            }

            jsonResponse.put("errors", ajaxErrors);
        }
        if (getPage() != null) {
            jsonResponse.put("page", getPage());
        }
        if (reqAttrs.size() != 0) {
            jsonResponse.put("reqAttrs", reqAttrs);
        }

        return jsonResponse;
    }

    public void switchLanguage() {
        if (reqParams.containsKey("language")) {
            Locale locale = new Locale(reqParams.get("language"));
            sessionAttrs.put("locale", locale);
            addRequestAttribute("language", locale.getLanguage());
        }
    }

    public String getMethod() {
        return this.request.getMethod();
    }

    public boolean isPost() {
        return this.getMethod().equals("POST");
    }

    public boolean isGet() {
        return this.getMethod().equals("GET");
    }

    public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(ResponseInfo responseInfo) {
        this.responseInfo = responseInfo;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        fillRequestParameters(request);
        fillRequestAttributes(request);

        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        fillSessionAttributes(session);

        this.session = session;
    }

    public void addRequestAttribute(String name, Object value) {
        reqAttrs.put(name, value);
    }

    public void removeRequestAttribute(String name) {
        reqAttrs.remove(name);
    }

    public Object getRequestAttribute(String name) {
        return reqAttrs.get(name);
    }

    public void addRequestParameter(String name, String value) {
        reqParams.put(name, value);
    }

    public void removeRequestParameter(String name) {
        reqParams.remove(name);
    }

    public String getRequestParameter(String name) {
        return reqParams.get(name);
    }

    public void addSessionAttribute(String name, Object value) {
        sessionAttrs.put(name, value);
    }

    public void removeSessionAttribute(String name) {
        session.removeAttribute(name);
        sessionAttrs.remove(name);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttrs.get(name);
    }

    public boolean hasRequestAttribute(String name) {
        for (Map.Entry<String, String> param : reqParams.entrySet()) {
            if (param.getKey().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void setPage(String route) {
        responseInfo.setPage(router.getPage(route));
    }

    public void setPage(Page page) {
        responseInfo.setPage(page);
    }

    public Page getPage() {
        return responseInfo.getPage();
    }

    public void setIsUpdated(boolean isUpdated) {
        responseInfo.setIsUpdated(isUpdated);
    }

    public boolean isUpdated() {
        return responseInfo.isUpdated();
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getAllErrors() {
        return errors.getAllErrors();
    }

    public void setAllErrors(Map<String, List<String>> errors) {
        this.errors.setAllErrors(errors);
    }

    public void addErrors(Errors errors) {
        getAllErrors().putAll(errors.getAllErrors());
    }

    public void addErrors(Map<String, List<String>> errors) {
        getAllErrors().putAll(errors);
    }

    public void addError(String field, String message) {
        errors.addFieldError(field, message);
    }

    public String getTitle() {
        return responseInfo.getTitle();
    }

    public void setTitle(String title) {
        responseInfo.setTitle(title);
    }

    public int getHttpError() {
        return responseInfo.getHttpError();
    }

    public void setHttpError(int httpError) {
        responseInfo.setHttpError(httpError);
    }

    public void setAjax(boolean isAjax) {
        this.responseInfo.setAjax(isAjax);
    }

    public boolean isAjax() {
        return this.responseInfo.isAjax();
    }

    public User getUser() {
        return (User) this.sessionAttrs.get("user");
    }

    public void setUser(User user) {
        this.sessionAttrs.put("user", user);
    }

    public long getUserId() {
        return getUser().getId();
    }

    public boolean getBoolean(String field) {
        int fieldPosition = field.lastIndexOf("field.");

        if (fieldPosition == -1) {
            return getBoolean(field, field.replace('.', '-'));
        }
        String parameter = field.substring(field.lastIndexOf("field.") + 6).replace('.', '-');
        return getBoolean(field, parameter);
    }

    public boolean getBoolean(String field, String parameter) {
        return reqParams.get(parameter) != null && reqParams.get(parameter).equals("on");
    }

    public String getString(String field) {
        int fieldPosition = field.lastIndexOf("field.");

        if (fieldPosition == -1) {
            return getString(field, field.replace('.', '-'));
        }
        String parameter = field.substring(field.lastIndexOf("field.") + 6).replace('.', '-');
        return getString(field, parameter);
    }

    public String getString(String field, String parameter) {
        return reqParams.get(parameter);
    }

    public Timestamp getTimestamp(String field, String parameter) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date;
        try {
            date = dateFormatter.parse(getRequestParameter(parameter));
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            this.addError(field, "error.nad");
        }
        return new Timestamp(0);
    }

    public Enum getEnum(String sfield, String parameter, Class theClass) {
        try {
            Field[] fields = theClass.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isPublic(field.getModifiers())) {
                    Enum en = Enum.valueOf(theClass, field.getName().toUpperCase());
                    Method method = en.getClass().getMethod("getName");
                    if (method != null) {
                        if (((String) method.invoke(en)).equals(getRequestParameter(parameter))) {
                            return en;
                        }
                    }
                }
            }
        } catch (ReflectiveOperationException e) {
        }

        return null;
    }

    public Enum getEnum(String field, Class theClass) {
        int fieldPosition = field.lastIndexOf("field.");

        if (fieldPosition == -1) {
            return getEnum("field.".concat(field), field.replace('.', '-'), theClass);
        }
        String parameter = field.substring(field.lastIndexOf("field.") + 6).replace('.', '-');
        return getEnum(field, parameter, theClass);
    }

    public BigDecimal getBigDecimal(String field) {
        int fieldPosition = field.lastIndexOf("field.");

        if (fieldPosition == -1) {
            return getBigDecimal("field.".concat(field), field.replace('.', '-'));
        }
        String parameter = field.substring(field.lastIndexOf("field.") + 6).replace('.', '-');
        return getBigDecimal(field, parameter);
    }

    public BigDecimal getBigDecimal(String field, String parameter) {
        if (getRequestParameter(parameter) == null) {
            return null;
        }

        DecimalFormat decimalFormat = new DecimalFormat();

        try {
            return new BigDecimal(decimalFormat.parse(getRequestParameter(parameter)).toString());
        } catch (ParseException e) {
            this.addError(field, "error.nan");
        }

        return null;
    }

    public Timestamp getTimestamp(String field) {
        int fieldPosition = field.lastIndexOf("field.");

        if (fieldPosition == -1) {
            return getTimestamp("field.".concat(field), field.replace('.', '-'));
        }
        String parameter = field.substring(field.lastIndexOf("field.") + 6).replace('.', '-');
        return getTimestamp(field, parameter);
    }

    public long getLong(String field, String parameter) {
        long value = -1;
        try {
            value = Long.parseLong(reqParams.get(parameter));
        } catch (NumberFormatException e) {
            this.addError(field, "error.nan");
        }
        return value;
    }

    public long getLong(String field) {
        int fieldPosition = field.lastIndexOf("field.");

        if (fieldPosition == -1) {
            return getLong("field.".concat(field), field.replace('.', '-'));
        }
        String parameter = field.substring(field.lastIndexOf("field.") + 6).replace('.', '-');
        return getLong(field, parameter);
    }

    public int getInt(String field, String parameter) {
        int value = 0;
        try {
            value = Integer.parseInt(reqParams.get(parameter));
        } catch (NumberFormatException e) {
            this.addError(field, "error.nan");
        }
        return value;
    }

    public int getInt(String field) {
        String parameter = field.substring(field.lastIndexOf("field.") + 6);
        return getInt(field, parameter);
    }

    public double getDouble(String field, String parameter) {
        double value = 0;
        try {
            value = Double.parseDouble(reqParams.get(parameter));
        } catch (NumberFormatException e) {
            this.addError(field, "error.nan");
        }
        return value;
    }

    public double getDouble(String field) {
        String parameter = field.substring(field.lastIndexOf("field.") + 6);
        return getDouble(field, parameter);
    }
}
