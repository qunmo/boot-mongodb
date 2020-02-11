package sjz.sgy.lb.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileTools {
	
	
	//定义图片上传的文件目录
	private static String FILE_BASE_PATH = "/root/clkj/pic";
	
	/**
	 * @description 单文件上传
	 * @param file
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(MultipartFile file) throws IOException {
		String extName = "";
		String filePath = FILE_BASE_PATH;
		String originalFilename = file.getOriginalFilename();// 得到上传的文件名
		extName = originalFilename.substring(originalFilename.lastIndexOf("."));// 得到图片的后缀
		// 为了不让图片不重复进行相关操作
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTime = sdf.format(new Date());
		int randomCode = (int) (Math.random() * (999 - 100) + 100);
		// 生成新文件名
		String fileName = nowTime + randomCode + extName;

		// 创建了一个File对象，名字是newFile，路径是filePath，名字是filename。
		File newFile = new File(filePath, fileName);
		if (!newFile.exists()) {
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}
			newFile.createNewFile();
		}
		FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
		String picDataPath = "pic/" + fileName;
		return picDataPath;
	}
	
}
