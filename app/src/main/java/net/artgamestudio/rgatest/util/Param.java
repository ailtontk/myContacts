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
     * Used for database
     */
    public abstract class Database {
        public static final String NAME = "myContacts";
        public static final int VERSION = 1;
    }

    /**
     * Used for database tables
     */
    public abstract class Tables {
        public static final String CONTACTS = "contacts";
    }

    /**
     * Used for component contact operations
     */
    public abstract class ComponentCompact {
        public static final int CONTACTS_SERVICE_RESPONSE = 1;
        public static final int CONTACTS_IMPORTED = 2;
        public static final int CONTACTS_LIST_ITEM_CLICKED = 3;
        public static final int CONTACTS_LIST_ITEM_LONG_CLICKED = 4;
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
        public static final String CONTACT = "CONTACT";
        public static final String CONTACT_ID = "CCONTACT_ID";
        public static final String CONTACT_POSITION = "CONTACT_POSITION";
    }
}
