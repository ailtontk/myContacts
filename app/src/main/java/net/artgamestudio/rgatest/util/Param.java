package net.artgamestudio.rgatest.util;

/**
 * Created by ailtonramos on 21/09/2017.<br>
 *
 * Used to share params between classes (when Resources isn't available)
 */
public abstract class Param {

    public abstract class Net {
        public static final String URL = "https://s3-sa-east-1.amazonaws.com/rgasp-mobile-test/v1/";
    }

    public abstract class Database {
        public static final String NAME = "rga";
        public static final int VERSION = 1;
    }

    public abstract class Contact {
        public static final int CONTACTS_SERVICE_RESPONSE = 1;
        public static final int CONTACTS_IMPORTED = 2;
    }

    public abstract class Intent {

    }
}
