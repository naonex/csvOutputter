
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 接続先設定画面（子画面）
 */
public class SubForm extends Stage {

	public void start() throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/SubForm.fxml"));
		
		Parent root = loader.load();
		Scene scene = new Scene(root, 500, 200);
		 
		//サイズ固定
		this.setResizable(false);
		//子画面起動中は親画面を操作できなくする
		this.initModality(Modality.APPLICATION_MODAL);
		
		this.setTitle("connection setting");
		this.setScene(scene);
		this.show();
	}
 
    private void setTextFields(String preposition) {
    	PropertyManager proManager = new PropertyManager();
    	sqlServerUrl.setText(proManager.properties.getProperty(preposition + ".sqlServerUrl"));
    	tableName.setText(proManager.properties.getProperty(preposition + ".tableName"));
    }
	
    /* 
     * @FXML定義・イベント
     */
    @FXML
    private TextField sqlServerUrl;    
    @FXML
    private TextField tableName;
    
    @FXML
    private void initialize() {
    	setTextFields("val");
    }
    
    @FXML
    private void clickReturnDefaultValuesButton(ActionEvent event) {
    	setTextFields("def");
    }
    
    @FXML
    private void clickReturnValidValuesButton(ActionEvent event) {
    	setTextFields("val");
    }
    
    @FXML
    private void clickConfirmButton(ActionEvent event) {
    	PropertyManager proManager = new PropertyManager();
    	proManager.properties.setProperty("val.sqlServerUrl",sqlServerUrl.getText());
    	proManager.properties.setProperty("val.tableName",tableName.getText());
    	proManager.write();
    	
        Stage stage = (Stage) ((Node)event.getTarget()).getScene().getWindow();
        stage.close();
    }
    
}