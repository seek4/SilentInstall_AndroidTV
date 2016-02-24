package com.changhong.appcontrol.operation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class OperationUtils {

	private OperationUtils(){
		
	}
	
	/**	ÏÂÔØÓ¦ÓÃ´æ·ÅÄ¿Â¼*/
	public static String fileDir = Environment.getExternalStorageDirectory().getPath()+"/appControl/";
	
	public static String downloadPackage(String packageUrl) throws Exception{
		String[] tmpStrings = packageUrl.split("/");
		String filePath = fileDir+tmpStrings[tmpStrings.length-1];
		Log.i("yangtong","filePath >>"+filePath);
		if(!new File(fileDir).exists()){
			Log.i("yangtong","!new File(fileDir).exists()");
			new File(fileDir).mkdir();
		}
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		URL url = new URL(packageUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		InputStream  inputStream = urlConnection.getInputStream();
		OutputStream outputStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int length = -1;
		while((length = inputStream.read(buffer))>0){
			outputStream.write(buffer,0,length);
		}
		outputStream.flush();
		if(inputStream!=null){
			inputStream.close();
		}
		if(outputStream!=null){
			outputStream.close();
		}
		return filePath;
	}
	
	public static boolean installPackage(int type,String packageUrl){
		Log.i("yangtong","installPackage >>"+packageUrl);
		String filePath;
		try {
			filePath = downloadPackage(packageUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("yangtong","ÏÂÔØ"+packageUrl+"Ê§°Ü");
			e.printStackTrace();
			return false;
		}
		boolean result = false;
		switch (type) {
		case 1:	//	
			result = installSilent(filePath);
		case 2:	//	ï¿½ï¿½×°ï¿½ï¿½Éºï¿½ï¿½Öªï¿½Ã»ï¿½		
			break;
		case 3:	//	ï¿½Ã»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ç·ï¿½×°		
			break;

		default:
			result = installSilent(filePath);
		}
		File packageFile = new File(filePath);
		if(packageFile.exists()){
			packageFile.delete();
		}
		return result;
	}
	
	public static boolean updatePackage(int type,String packageName,String packageUrl){
		if(uninsPackage(0, packageName)){
			if(installPackage(type, packageUrl))
				return true;
		}
		return false;
	}
	
	public static boolean uninsPackage(int type,String packageName){
		Log.i("yangtong","uninsPackage >>"+packageName);
		switch (type) {
		case 0:
			return uninsSilent(packageName);
		case 1:
			return uninsSilent(packageName);
		default:
			return uninsSilent(packageName);
		}
	}
	
	
	
	/**
	 * ¾²Ä¬°²×°
	 */
	private static boolean installSilent(String packageUrl){
		String[] args = {"pm","install","-r",packageUrl}; 
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while((read=errIs.read())!=-1){
				baos.write(read);
			}
			inIs = process.getInputStream();
			read = -1;
			while((read=inIs.read())!=-1){
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(inIs!=null)
					inIs.close();
				if(errIs!=null)
					errIs.close();
				if(baos!=null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(process!=null)
				process.destroy();
		}
		return result.contains("uccess")?true:false;
	}
	
	private static boolean uninsSilent(String packageName){
		String[] args = {"pm","uninstall",packageName};
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while((read=errIs.read())!=-1){
				baos.write(read);
			}
			inIs = process.getInputStream();
			read = -1;
			while((read=inIs.read())!=-1){
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(inIs!=null)
					inIs.close();
				if(errIs!=null)
					errIs.close();
				if(baos!=null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(process!=null)
				process.destroy();
		}
		Log.i("yangtong","unins result >>"+result);
		return result.contains("uccess")?true:false;
	}
	
}
