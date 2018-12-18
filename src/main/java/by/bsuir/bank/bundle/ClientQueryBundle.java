package by.bsuir.bank.bundle;

/**
 * Class is a com.epam.au.bundle for database static queries.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ClientQueryBundle extends QueryBundle {
    private static volatile ClientQueryBundle instance;

    private ClientQueryBundle(String bundleName) {
        super(bundleName);
    }

    public static ClientQueryBundle getInstance() {
        if (instance == null)
            synchronized (ClientQueryBundle.class) {
                if (instance == null)
                    instance = new ClientQueryBundle(BundleNamesStore.CLIENT_QUERY_BUNDLE);
            }
        return instance;
    }
}
