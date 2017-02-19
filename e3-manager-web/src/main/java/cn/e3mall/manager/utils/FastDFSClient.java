package cn.e3mall.manager.utils;


import java.net.URLDecoder;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {

	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient1 storageClient = null;
	// fdfsclient的配置文件的路径
	private String CONF_NAME = "/fdfs/fdfs_client.conf";

	public FastDFSClient() throws Exception {

		// 配置文件必须指定全路径
		String confName = this.getClass().getResource(CONF_NAME).getPath();
		// 配置文件全路径中如果有中文，需要进行utf8转码
		confName = URLDecoder.decode(confName, "utf8");

		ClientGlobal.init(confName);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}

	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param fileName
	 *            文件全路径
	 * @param extName
	 *            文件扩展名，不包含（.）
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String result = storageClient.upload_file1(fileName, extName, metas);
		return result;
	}

	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}

	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}

	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param fileContent
	 *            文件的内容，字节数组
	 * @param extName
	 *            文件扩展名
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

		String result = storageClient.upload_file1(fileContent, extName, metas);
		return result;
	}

	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}

	public String uploadFile(byte[] fileContent, String extName) throws Exception {
		return uploadFile(fileContent, extName, null);
	}
	
	public int deleteFile(String remoteFile) throws Exception {
		if(remoteFile == null || remoteFile.trim().equals("")) {
			return -1;
		}
		//处理远程文件，去除域名部分
		//例如：http://www.group.com/group1/M00/00/00/wKh0jFh6Oj2AGZibAAFUYYoIQxc571.png
		//处理效果：group1/M00/00/00/wKh0jFh6Oj2AGZibAAFUYYoIQxc571.png
		//找到倒数第5个/所在索引

		int index = -1;
		int length = remoteFile.length();
		
		for (int i=1;i<=5;i++) {
			if (length<1) {
				return -1; //剩下的至少是groupN
			}
			
			index = remoteFile.lastIndexOf("/",length-1);
			if (index<0) {
				//如果查询不到/
				if (i<4) {
					return -1; //少于4个/返回-1
				} else {
					break;
				}
			} else {
				//如果查询到/
				length = index;
			}
			if (i==5) {
				remoteFile = remoteFile.substring(index+1);
			}
		}
		
		//再用正则匹配验证一下
		String regex = "group\\d+/[a-zA-Z][A-F0-9]{2,}+/[A-F0-9]{2,}+/[A-F0-9]{2,}+/[\\w|-]+[.]?\\w+";
		if (!remoteFile.matches(regex)) {
			return -1;
		}
		
		//删默认图
		int result = storageClient.delete_file1(remoteFile);
		System.err.println("delete remotefile: "+remoteFile);
				
		//删原图例如：group1/M00/00/00/wKh0jFh6N9KAOX6jAADoCTH0z9U365_big.jpg
		int lastIndexOf = remoteFile.lastIndexOf(".");
		String bigFile = "";
		if (lastIndexOf == -1) {
			bigFile = remoteFile + "_big";
		} else {
			bigFile = remoteFile.substring(0, lastIndexOf) + "_big" + remoteFile.substring(lastIndexOf);
			
		}
		System.err.println("delete remotefile: "+bigFile);
		storageClient.delete_file1(bigFile);
		//storageClient.truncate_file1(remoteFile);
		
		//返回int类型的结果值0:文件删除成功，2：文件不存在 ，其它：文件删除出错
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String ss = "http://192.168.116.140/group1/M00/00/00/wKh0jFh7qi-AZGtHAABetsdXONQ640.png";
		
		FastDFSClient fastDFSClient = new FastDFSClient();
		int deleteFile = fastDFSClient.deleteFile(ss);
		System.out.println("返回结果："+deleteFile);
		
	}
}
