package sk.upjs.ics.android.koncovyprojekt2.provider;

    import android.provider.BaseColumns;

public interface Provider {
    public interface Test extends BaseColumns {
        public static final String TABLE_NAME = "TEST";
        public static final String TEST_LENGTH = "TEST_LENGTH";
        public static final String TEST_TYPE = "TEST_TYPE";
        public static final String TEST_DATE = "TEST_DATE";

    }
}


