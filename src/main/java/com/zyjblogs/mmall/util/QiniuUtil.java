package com.zyjblogs.mmall.util;


import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;

import com.qiniu.common.QiniuException;  
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;



//https://search.maven.org/artifact/com.qiniu/qiniu-java-sdk/7.0.3/jar
public class QiniuUtil {
      
    /**基本配置-从七牛管理后台拿到*/  
  //设置好账号的ACCESS_KEY和SECRET_KEY  
  static String ACCESS_KEY = "JXbPCLF2PQ7ORTViOilqEoYIbnQXoVNKGYC_KWBp";
  static String SECRET_KEY = "rxyLZnzwRHtTxtWNS9D5uKK5e_JWfmvaJzsFooUG";
  //要上传的空间名--  
  static String bucketName = "zyjmmall";
    
    
    
//  /** {"hash":"FrQF5eX_kNsNKwgGNeJ4TbBA0Xzr","key":"aa1.jpg"} 正常返回 key为七牛空间地址 http:/xxxx.com/aa1.jpg */
  //上传文件的路径  
//  String FilePath ="D:\\1.png";  
////上传到七牛后保存的文件名    访问为：http://oswj11a86.bkt.clouddn.com/daimo6.png
//  String key = "daimo6.png";    
//  
//  //密钥配置
//  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//  //创建上传对象
//  UploadManager uploadManager =new UploadManager(new Configuration());
//
//
//  //简单上传，使用默认策略，只需要设置上传的空间名就可以了
//  public String getUpToken(){
//      return auth.uploadToken(bucketname);
//  }
//

  public static void uploadFile(File file){
    try {  
    	UploadManager uploadManager =new UploadManager();
    	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
       String token = auth.uploadToken(bucketName);    
        //调用put方法上传  
      Response res = uploadManager.put(file, file.getName(), token);
      //打印返回的信息  
      System.out.println(res.bodyString());  
      System.out.println(res.statusCode);//200为上传成功
      } catch (QiniuException e) {  
          Response r = e.response;  
          // 请求失败时打印的异常的信息  
          System.out.println(r.toString());  
          try {  
              //响应的文本信息  
            System.out.println(r.bodyString());  
          } catch (QiniuException e1) {  
              //ignore  
          }  
      }         
  }  
  
  public static void main(String args[]) throws IOException{
    new QiniuUtil().uploadFile(new File("d:\\7206e2e0d2554695.jpg"));
  }  
  
}  
