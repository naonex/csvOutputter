  
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
    	//�ݒ�t�@�C�����[�h�i�������j
    	new PropertyManager();
    	
    	//��ʐݒ�
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainForm.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 200);
        
        //�T�C�Y�Œ�
        stage.setResizable(false);
    
        stage.setTitle("csvOutputter");
        stage.setScene(scene);
        stage.show();
    }
      
    /* 
     * @FXML��`�E�C�x���g
     */
    @FXML
    private Label javaVersion;
    
    @FXML
    private TextField filePath;
    
    /**
     * ���������i���sJava�o�[�W������\���j
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
        fileChooser.setTitle("�t�@�C���I��");
        
        //���[�U�[�z�[���������\���ɂ���
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //�t�@�C���p�X���ݒ肳��Ă���΁A�����\�����ύX
        File targetFile = new File(filePath.getText());
        if (targetFile.exists()) {
        	if (targetFile.isDirectory()) {
        		fileChooser.setInitialDirectory(targetFile);
        	} else if (Objects.nonNull(targetFile.getParent())) {
            	File parentDir = new File(targetFile.getParent());
                fileChooser.setInitialDirectory(parentDir);
            }
        }
        
        //�t�@�C���I���E�B���h�E�\�����́A�e��ʂ𑀍�s�ɂ���
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
			dialog.setContentText("�t�@�C�����o�͂��܂����B");
    	} catch (Exception e) {
			dialog = new Alert(AlertType.ERROR);
			dialog.setContentText("�t�@�C���̏o�͂Ɏ��s���܂����B");
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
    		dialog.setContentText("�ڑ���ݒ��ʂ̕\���Ɏ��s���܂����B");
    		dialog.setHeaderText(null);    	
        	dialog.showAndWait();
		}
	} 

}