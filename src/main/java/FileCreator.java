import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
 
public class FileCreator {
     
    public static void outputFile(String outputPath, ArrayList<String> outputLineList) throws IOException {
     
    	// �o�̓t�@�C���̍쐬
        try (FileWriter f = new FileWriter(outputPath, false);
        		PrintWriter p = new PrintWriter(new BufferedWriter(f));) {
            
            //outputLineList�̒��g���}�X�L���O��ɂ��ׂďo��
            ArrayList<String> maskingLineList =  maskingOutputLineList(outputLineList);
            for (String outputLine : maskingLineList) {
                p.print(outputLine);
                p.println();
            }
 
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
         
    }
    
    private static ArrayList<String> maskingOutputLineList(ArrayList<String> outputLineList) {
    	ArrayList<String> maskingLineList = new ArrayList<String>();
    	
        //outputLineList�̒��g���}�X�L���O��1�s�ڂ̓w�b�_�s�Ȃ̂ŃX�L�b�v
    	maskingLineList.add(outputLineList.get(0));
        for (int i = 1; i < outputLineList.size(); i++) {
        	String[] params = outputLineList.get(i).split(",", 0);
        	
        	ArrayList<String> maskingLine = new ArrayList<String>();
        	for (String param : params) {
        		//���������̔������}�X�L���O����i����؂�Ȃ��ꍇ�͗]����܂ށj
        		int paramLength = param.length();
        		int maskingLength = paramLength / 2 + paramLength % 2;
        		
        		String maskStr = new String(new char[maskingLength]).replace("\0", "*");
        		String maskingParam = param.replaceFirst(param.substring(0, maskingLength), maskStr);
        		maskingLine.add(maskingParam);
        	}
        	
        	maskingLineList.add(String.join(",", maskingLine));
        }
    	
    	return maskingLineList;
    }
 
}