package com.jackpon.util;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
@Service
public class SessionKeyOpenId {
	public JSONObject getSessionKeyOrOpenId(String code){
	    //微信端登录code
	    String wxCode = code;
	    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
	    Map<String,String> requestUrlParam = new HashMap<String, String>(  );
	    String appid = "";//"wx6daeaed921f80da8";
	    String secret = "";//"0dc4d0c46866b41af61a00cdf801a800";
	    requestUrlParam.put( "appid","" );//小程序appId
	    requestUrlParam.put( "secret","" );//小程序secret
	    requestUrlParam.put( "js_code",wxCode );//小程序端返回的code
	    requestUrlParam.put( "grant_type","authorization_code" );//默认参数
	 
	    //发送post请求读取调用微信接口获取openid用户唯一标识
	    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.httpPost(requestUrl,requestUrlParam));
	    return jsonObject;
	}
	public JSONObject getUserInfo(String encryptedData,String sessionKey,String iv){
	    // 被加密的数据
	    byte[] dataByte = Base64.decode(encryptedData);
	    // 加密秘钥
	    byte[] keyByte = Base64.decode(sessionKey);
	    // 偏移量
	    byte[] ivByte = Base64.decode(iv);
	    try {
	        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
	        int base = 16;
	        if (keyByte.length % base != 0) {
	            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
	            byte[] temp = new byte[groups * base];
	            Arrays.fill(temp, (byte) 0);
	            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
	            keyByte = temp;
	        }
	        // 初始化
	        Security.addProvider(new BouncyCastleProvider());
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
	        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
	        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
	        parameters.init(new IvParameterSpec(ivByte));
	        cipher.init( Cipher.DECRYPT_MODE, spec, parameters);// 初始化
	        byte[] resultByte = cipher.doFinal(dataByte);
	        if (null != resultByte && resultByte.length > 0) {
	            String result = new String(resultByte, "UTF-8");
	            return JSON.parseObject(result);
	        }
	    } catch (NoSuchAlgorithmException e) {
	    } catch (NoSuchPaddingException e) {
	    } catch (InvalidParameterSpecException e) {
	    } catch (IllegalBlockSizeException e) {
	    } catch (BadPaddingException e) {
	    } catch (UnsupportedEncodingException e) {
	    } catch (InvalidKeyException e) {
	    } catch (InvalidAlgorithmParameterException e) {
	    } catch (NoSuchProviderException e) {
	    }
	    return null;
	}
}
