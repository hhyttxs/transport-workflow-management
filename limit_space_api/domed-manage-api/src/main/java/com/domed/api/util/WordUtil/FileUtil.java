package com.domed.api.util.WordUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.zip.ZipOutputStream;

@Slf4j
public class FileUtil {



	/**
	 * 压缩文件
	 * @Description:
	 * @since  2012-11-30
	 * @author zhaogr
	 * @param @param inputFileName
	 * @param @param zipPath
	 * @param @throws Exception
	 * @return void
	 */
    public  void zip(String inputFileName,String zipPath) throws Exception {
        String zipFileName = zipPath; //打包后文件名字
        zip(zipFileName, new File(inputFileName));
    }

    private void zip(String zipFileName, File inputFile) {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			zip(out, inputFile, "");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


    }

    private void zip(ZipOutputStream out, File f, String base) {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           //out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           //out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = null;
			try {
				in = new FileInputStream(f);
				int b;
				while ( (b = in.read()) != -1) {
					out.write(b);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
       }
    }




	/**
	 * 删除目录及其子文件
	 * @param file
	 */
	public static void deleteDir(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0;i < files.length;i ++) {
					deleteDir(files[i]);
				}
				file.delete();
			}
		}
	}


    /*
     * 二进制转成图片
     */
	public static void base64StringToImage(String base64String,String url){
        try {
        	BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 =ImageIO.read(bais);
            File w2 = new File(url);//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] ff = file.listFiles();
			for (int i = 0; i < ff.length; i++) {
				deleteFile(ff[i].getPath());
			}
		}
		file.delete();
	}

	public static void createDirectory(String path) {
		File file = new File(path);

		if (file.exists()) {
			deleteFile(path);
		}

		file.mkdirs();
	}


	public static void createFile(String path) {
		File file = new File(path);

		if (file.exists()) {

		}else{
			file.mkdirs();
		}


	}





	public static void CopyFileWjj(String sourcePath, String targetPath) {
		try {
			//sourcePath 文件路径
			//targetPath 需要转的路径
			File file = new File(sourcePath);
			File filelist[] = null;
			if (file.exists()) {
				filelist = file.listFiles();
			}
			if(file!=null && filelist.length!=0){
				for (int i = 0; i < filelist.length;i++) {
					fileChannelCopy(filelist[i].getCanonicalPath(),targetPath+filelist[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void fileChannelCopy(String sourcePath, String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);

		if (!source.exists()) {
			return;
		}


		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			fi = new FileInputStream(source);
			fo = new FileOutputStream(target);
			// 得到对应的文件通道
			in = fi.getChannel();
			// 得到对应的文件通道
			out = fo.getChannel();
			// 连接两个通道，并且从in通道读取，然后写入out通道
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fi != null){
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fo != null){
				try {
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void fileChannelCopy(File file, String targetPath) {

		File target = new File(targetPath);

		if (!file.exists()) {
			return;
		}


		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			fi = new FileInputStream(file);
			fo = new FileOutputStream(target);
			// 得到对应的文件通道
			in = fi.getChannel();
			// 得到对应的文件通道
			out = fo.getChannel();
			// 连接两个通道，并且从in通道读取，然后写入out通道
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fi != null){
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fo != null){
				try {
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//图片转码
	public static String ImagetoEncode(String path) {
		InputStream in = null;
		byte[] data = null;
		try {
			File file=new File(path);
			if(!file.exists()){
				return " ";
			}
			in = new FileInputStream(path);

			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}



	/**
	 * 图片截取 参数：图片流，图片名称,截取后的宽 ,截取后的高； 返回值：图片流;
	 * @param fileinfo
	 * @param filename
	 * @return
	 * @throws Exception
	 */
//	public static String  ImageCapture(File urlfile,String suffix,int width,int height) {
//		String fileinfo="";
//		try {
//			String src=urlfile.getPath();
//			// 取得图片读入器
//			Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
//			ImageReader reader = (ImageReader) readers.next();
//			// 取得图片读入流
//			InputStream source = new FileInputStream(src);
//			ImageInputStream iis = ImageIO.createImageInputStream(source);
//			reader.setInput(iis, true);
//			// 图片参数
//			ImageReadParam param = reader.getDefaultReadParam();
//			Rectangle rect = new Rectangle(0, 0, width, height);
//			param.setSourceRegion(rect);
//			BufferedImage bi = reader.read(0, param);
//			ImageIO.write(bi, suffix, new File(src));
//			//生成截取后的图片完成
//			File f = new File(src);
//			try {
//				fileinfo =  ImageUtil.image2String(f);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return fileinfo;
//	}



/*

	 * 华晨图片截取
	 * @param fileinfo
	 * @param filename
	 * @return
	 * @throws Exception

	public static String  HuaChenImageCapture(File files,String filename) {
		try {
			String url="D:\\SYMP\\CUTOUT\\";

			File urlfile = new File(url);

			//判断文件夹是否存在,如果不存在则创建文件夹
			if (!urlfile.exists()) {
				urlfile.mkdirs();
			}

			// 2.先读取图片的尺寸
			File file = new File(files.getPath());
			int[]  arr = getImgWidthHeight(file);
			int windth =arr[0];
			int hight =arr[1];

			//3.判断是否截取
			if(windth>=1440   &&  hight>=900){ //需要截取的
				String suffix ="";  //图片类型 ， （后缀）
				if(filename.lastIndexOf(".")!=-1){
					suffix=filename.substring(filename.lastIndexOf(".")+1, filename.length());
				}
				//ImageCapture(file,suffix,530,555);
				ImageCapture(file,suffix,1150,850);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
*/
//	public static void main(String[] args) {
//
//		  String path="D:\\SYMP\\AAA\\";
//		  File file=new File(path);
//		  File[] tempList = file.listFiles();
//		  for (int i = 0; i < tempList.length; i++) {
//			  System.out.println(tempList[i].getPath());
//			  FileUtil.HuaChenImageCapture(tempList[i],tempList[i].getName());
//		  }
	/*	String fileinfo="";
		File f = new File("D:\\SYMP\\ss.jpg");
		try {
			FileUtil.HuaChenImageCapture(f,"ss.jpg");
//			fileinfo =  ImageUtil.image2String(f);
			//String s =HuaChenImageCapture(fileinfo,"a.jpg");
			//System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
//	}

	/**
	 * 华晨图片截取
	 * @param fileinfo
	 * @param filename
	 * @return
	 * @throws Exception
	 */
//	public static void  HuaChenImageCapture(File files,String filename) {
//		//图片名字大于15位是才进入
//		//if(filename.length() > 15){
//			try {
//				// 1.先读取图片的尺寸
//				File file = new File(files.getPath());
//				int[]  arr = getImgWidthHeight(file);
//				int windth =arr[0];
//				int hight =arr[1];
//
//				//2.判断是否截取（一类导出的图片）
//				if(windth == 1440 && hight == 900){ //需要截取的
//					String suffix ="";  //图片类型 ， （后缀）
//					if(filename.lastIndexOf(".")!=-1){
//						suffix=filename.substring(filename.lastIndexOf(".")+1, filename.length());
//					}
//					ImageCapture(file,suffix,1252,894);
//				}
//				//（另一类导出的图片，上海的图片后缀为jpg，其实为png）
//				if(windth == 800 && hight == 600){ //需要截取的
//
//					ImageCapture(file,"png",530,559);
//				}
//
//				//宁夏二队  红外检漏
//				if(windth == 640 && hight == 480){ //需要截取的
//					String suffix ="";  //图片类型 ， （后缀）
//					if(filename.lastIndexOf(".")!=-1){
//						suffix=filename.substring(filename.lastIndexOf(".")+1, filename.length());
//					}
//					ImageCapture(file,suffix,548,405);
//				}
//
//			} catch (
//					Exception e) {
//				e.printStackTrace();
//			}
//		//}
//	}


	/**
	 *生成报告分部缓存文件
	 * @param data
	 * @return
	 */
	public static String creatReportFile(String data,String filePath){

		File dir = new File(filePath);
		if (!dir.exists()) {
			try {
				dir.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(data.getBytes());

		BufferedInputStream inBuff = null;
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(tInputStringStream);

			output = new FileOutputStream(filePath);

			outBuff = new BufferedOutputStream(output);

			// 缓冲数组
			byte[] b = new byte[1024 * 1024];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
			// result = "OK";
			// return SUCCESS;
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			if (inBuff != null) {
				try {
					inBuff.close();
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
			if (outBuff != null) {
				try {
					outBuff.close();
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
		}
		return dir.getName();
	}
	/**
	 *生成报告分部缓存文件
	 * @param data
	 * @return
	 */
	 public static String  readFileByLines(String filePath) {

		StringBuffer data=new StringBuffer();
		if(!filePath.equals("")){
	        File file = new File(filePath);
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	            	data.append(tempString);
	                // 显示行号
	            }
	            reader.close();
	        } catch (IOException e) {
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		}
	        return data.toString();
	}

	 /**
	  *
	  * @param file
	  * @return
	  */
	 public static String GetFileSize(Long fileS){
		    String size = "";

		      DecimalFormat df = new DecimalFormat("#.00");
	           if (fileS < 1024) {
	               size = df.format((double) fileS) + "BT";
	           } else if (fileS < 1048576) {
	               size = df.format((double) fileS / 1024) + "KB";
	           } else if (fileS < 1073741824) {
	               size = df.format((double) fileS / 1048576) + "MB";
	           } else {
	               size = df.format((double) fileS / 1073741824) +"GB";
	           }

		    return size;
		   }

	/**
	 * MultipartFile 转 File
	 *
	 * @param file
	 * @throws Exception
	 */
	public static File multipartFileToFile(MultipartFile file) throws Exception {

		File toFile = null;
		if (file.equals("") || file.getSize() <= 0) {
			file = null;
		} else {
			InputStream ins = null;
			ins = file.getInputStream();
			toFile = new File(file.getOriginalFilename());
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		return toFile;
	}

	/**
	 * MultipartFile 转 File
	 *
	 * @param file
	 * @throws Exception
	 */
	public static File multipartFileToFile(MultipartFile file,File toFile) throws Exception {
		if (file.equals("") || file.getSize() <= 0) {
			file = null;
		} else {
			InputStream ins = null;
			ins = file.getInputStream();
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		return toFile;
	}

	//获取流文件
	private static void inputStreamToFile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
