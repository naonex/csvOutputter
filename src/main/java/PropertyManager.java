import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyManager {

    private final String INIT_FILE_PATH = "csvOutputter.properties";
    public Properties properties;

    public PropertyManager() {
        this.properties = new Properties();
        try {
        	this.properties.load(new FileInputStream(INIT_FILE_PATH));
            init();
        } catch (Exception e) {
            // ファイル読み込みに失敗
            System.out.println(String.format("設定ファイルの読み込みに失敗しました。ファイル名:%s", INIT_FILE_PATH));
        }
    }
    
    /**
     * 初期処理
     */
    private void init() {
    	initProperty("val.sqlServerUrl","def.sqlServerUrl");
    	initProperty("val.tableName","def.tableName");
    	//初期化したプロパティ値をファイルに書き込む
    	write();
    }
    
    /**
     * プロパティ値を初期化する。キーに値が設定されていなければ、デフォルトキーの値をセット
     *
     * @param key キー
     * @param defaultKey デフォルトキー
     */
    private void initProperty(String key, String defaultKey) {
    	String value = this.properties.getProperty(key);
    	if (Objects.isNull(value) || value.length() <= 0) {
    		value = this.properties.getProperty(defaultKey);
    		this.properties.setProperty(key, value);
    	}
    }
    
    /**
     * 現在の設定値をファイルに書き込む
     */
    public void write() {
    	try {
    		this.properties.store(new FileOutputStream(INIT_FILE_PATH), "csvOutputter.properties");	
    	} catch (Exception e) {
    		//設定値書き込み失敗
    		System.out.println(String.format("設定ファイルの書き込みに失敗しました。ファイル名:%s", INIT_FILE_PATH));
    	}
    }

}