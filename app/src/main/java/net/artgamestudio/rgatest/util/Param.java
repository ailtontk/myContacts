package net.artgamestudio.rgatest.util;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Used to share params between classes (when Resources isn't available)
 */
public abstract class Param {

    /**
     * Used for network operations
     */
    public abstract class Net {
        public static final String URL = "https://s3-sa-east-1.amazonaws.com/rgasp-mobile-test/v1/";
    }

    /**
     * Used for Shared Preferences keys
     */
    public abstract class Database {
        public static final String NAME = "myContacts";
    }

    /**
     * Used for component contact operations
     */
    public abstract class Contact {
        public static final int CONTACTS_SERVICE_RESPONSE = 1;
        public static final int CONTACTS_IMPORTED = 2;
    }

    /**
     * Used for Shared Preferences keys
     */
    public abstract class Prefs {
        public static final String IMPORTED = "IMPORTED";
    }

    /**
     * Used for intent keys
     */
    public abstract class Intent {

    }
}
