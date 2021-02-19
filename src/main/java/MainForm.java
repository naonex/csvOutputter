  
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class MainForm extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	//設定ファイルロード（初期化）
    	new PropertyManager();
    	
    	//画面設定
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainForm.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 200);
        
        //サイズ固定
        stage.setResizable(false);
    
        stage.setTitle("csvOutputter");
        stage.setScene(scene);
        stage.show();
    }
      
    /* 
     * @FXML定義・イベント
     */
    @FXML
    private Label javaVersion;
    
    @FXML
    private TextField filePath;
    
    /**
     * 初期処理（実行Javaバージョンを表示）
     */
    @FXML
    private void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        this.javaVersion.setText("JavaFX " + javafxVersion + ", running on Java " + javaVersion);
    }
    
    @FXML
    private void clickReferButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("ファイル選択");
        
        //ユーザーホームを初期表示にする
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //ファイルパスが設定されていれば、初期表示先を変更
        File targetFile = new File(filePath.getText());
        if (targetFile.exists()) {
        	if (targetFile.isDirectory()) {
        		fileChooser.setInitialDirectory(targetFile);
        	} else if (Objects.nonNull(targetFile.getParent())) {
            	File parentDir = new File(targetFile.getParent());
                fileChooser.setInitialDirectory(parentDir);
            }
        }
        
        //ファイル選択ウィンドウ表示中は、親画面を操作不可にする
        targetFile = fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
        
        if (Objects.nonNull(targetFile)) {
        	filePath.setText(targetFile.getPath());
        }
    }
    
    @FXML
    private void clickExecButton(ActionEvent event) {
    	SqlServerConnector sqlServer = new SqlServerConnector();
    	
    	Alert dialog = null;
    	try {
    		ArrayList<String> tableDataList = sqlServer.getTableDataListInCsvFormat();
    		FileCreator.outputFile(filePath.getText(), tableDataList);
			dialog = new Alert(AlertType.INFORMATION);
			dialog.setContentText("ファイルを出力しました。");
    	} catch (Exception e) {
			dialog = new Alert(AlertType.ERROR);
			dialog.setContentText("ファイルの出力に失敗しました。");
    	}
		dialog.setHeaderText(null);    	
    	dialog.showAndWait();
    }
    
    @FXML
    private void clickConnectionSettingButton(ActionEvent event) {
    	SubForm subForm = new SubForm();
    	try {
    		subForm.start();
    	} catch (Exception e) {
        	Alert dialog = null;
    		dialog = new Alert(AlertType.ERROR);
    		dialog.setContentText("接続先設定画面の表示に失敗しました。");
    		dialog.setHeaderText(null);    	
        	dialog.showAndWait();
		}
	} 

}