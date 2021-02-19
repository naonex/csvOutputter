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
            // �t�@�C���ǂݍ��݂Ɏ��s
            System.out.println(String.format("�ݒ�t�@�C���̓ǂݍ��݂Ɏ��s���܂����B�t�@�C����:%s", INIT_FILE_PATH));
        }
    }
    
    /**
     * ��������
     */
    private void init() {
    	initProperty("val.sqlServerUrl","def.sqlServerUrl");
    	initProperty("val.tableName","def.tableName");
    	//�����������v���p�e�B�l���t�@�C���ɏ�������
    	write();
    }
    
    /**
     * �v���p�e�B�l������������B�L�[�ɒl���ݒ肳��Ă��Ȃ���΁A�f�t�H���g�L�[�̒l���Z�b�g
     *
     * @param key �L�[
     * @param defaultKey �f�t�H���g�L�[
     */
    private void initProperty(String key, String defaultKey) {
    	String value = this.properties.getProperty(key);
    	if (Objects.isNull(value) || value.length() <= 0) {
    		value = this.properties.getProperty(defaultKey);
    		this.properties.setProperty(key, value);
    	}
    }
    
    /**
     * ���݂̐ݒ�l���t�@�C���ɏ�������
     */
    public void write() {
    	try {
    		this.properties.store(new FileOutputStream(INIT_FILE_PATH), "csvOutputter.properties");	
    	} catch (Exception e) {
    		//�ݒ�l�������ݎ��s
    		System.out.println(String.format("�ݒ�t�@�C���̏������݂Ɏ��s���܂����B�t�@�C����:%s", INIT_FILE_PATH));
    	}
    }

}