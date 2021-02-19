import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
 
public class FileCreator {
     
    public static void outputFile(String outputPath, ArrayList<String> outputLineList) throws IOException {
     
    	// 出力ファイルの作成
        try (FileWriter f = new FileWriter(outputPath, false);
        		PrintWriter p = new PrintWriter(new BufferedWriter(f));) {
            
            //outputLineListの中身をマスキング後にすべて出力
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
    	
        //outputLineListの中身をマスキング※1行目はヘッダ行なのでスキップ
    	maskingLineList.add(outputLineList.get(0));
        for (int i = 1; i < outputLineList.size(); i++) {
        	String[] params = outputLineList.get(i).split(",", 0);
        	
        	ArrayList<String> maskingLine = new ArrayList<String>();
        	for (String param : params) {
        		//総文字数の半分をマスキングする（割り切れない場合は余りも含む）
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