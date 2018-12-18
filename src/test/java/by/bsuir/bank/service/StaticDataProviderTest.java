package by.bsuir.bank.service;

import by.bsuir.bank.entity.Disability;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class StaticDataProviderTest {
    private static StaticDataProvider staticDataProvider;

    @BeforeAll
    static void loadDependency() {
        staticDataProvider = new StaticDataProvider();
    }

    @AfterAll()
    static void checkNotDestroyObject() {
        assertNotNull(staticDataProvider);
    }

    @BeforeEach
    void checkDependencyNotNull() {
        assertNotNull(staticDataProvider);
    }

    @Test
    void getStaticDataByClassWithValidEnum() {
        List<String> names = staticDataProvider.getStaticDataByClassName(Disability.class.getName());

        assertNotNull(names);

        assertFalse(names.contains("name"));

        assertTrue(names.contains("First degree"));
        assertTrue(names.contains("Second degree"));
        assertTrue(names.contains("Third degree"));

        assertEquals(names.get(0), "First degree");
        assertEquals(names.get(1), "Second degree");
        assertEquals(names.get(2), "Third degree");
    }
}