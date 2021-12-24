
package com.funicorn.cloud.system.center.config;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.cloud.system.center.property.OssProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * minio 操作类 支持minio 和 AmazonS3
 * @author Aimee
 * @since 2021/09/30
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@RequiredArgsConstructor
public class OssTemplate {

	private final OssProperties ossProperties;

	private final MinioClient minioClient;

	/**
	 * 创建一个默认桶
	 * prefix:public
	 * prefix:private
	 * @param bucketName bucket名称
	 * @throws Exception 异常
	 */
	public void createBucket(String bucketName) throws Exception {
		if (!bucketExists(bucketName)) {
			MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
			minioClient.makeBucket(makeBucketArgs);
			initBucketPolicy(bucketName);
		}
	}

	/**
	 * 桶名称是否已存在
	 * @param bucketName 桶名称
	 * @return true/false
	 * @throws Exception 异常
	 * */
	public boolean bucketExists(String bucketName) throws Exception {
		BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
		return minioClient.bucketExists(bucketExistsArgs);
	}

	/**
	 * 修改桶设置
	 * @param bucketName 桶名称
	 * @throws Exception 异常
	 * */
	public void initBucketPolicy(String bucketName) throws Exception{
		if (!bucketExists(bucketName)) {
			return;
		}
		String policy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
		String filename = "config/bucket_policy.json";
		ClassPathResource classPathResource = new ClassPathResource(filename);
		String config = IOUtils.toString(classPathResource.getInputStream(), BaseConstant.CHARSET_UTF8).replaceAll("\\$\\{bucketName}",bucketName);
		SetBucketPolicyArgs args = SetBucketPolicyArgs.builder().bucket(bucketName).config(config).build();
		minioClient.setBucketPolicy(args);
	}

	/**
	 * @param bucketName bucket名称
	 * @throws Exception 异常
	 */
	public void removeBucket(String bucketName) throws Exception {
		if (bucketExists(bucketName)){
			minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
		}
	}

	/**
	 * 获取全部bucket
	 * @return List
	 * @throws Exception 异常
	 */
	public List<Bucket> listBuckets() throws Exception{
		return minioClient.listBuckets();
	}

	/**
	 * 获取指定桶名称
	 * @param bucketName bucket名称
	 * @return Bucket
	 * @throws Exception 异常
	 */
	public Bucket getBucket(String bucketName) throws Exception{
		Optional<Bucket> optionalBucket = listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
		return optionalBucket.orElse(null);
	}


	/**
	 * 根据文件前置查询文件
	 * @param bucketName bucket名称
	 * @param prefix 前缀
	 * @param recursive 是否递归查询
	 * @return Item 列表
	 */
	public Iterable<Result<Item>> listObjectsByPrefix (String bucketName, String prefix, Boolean recursive){
		ListObjectsArgs.Builder builder = ListObjectsArgs.builder().bucket(bucketName);
		if (StringUtils.isNotBlank(prefix)) {
			builder.prefix(prefix);
		}
		builder.recursive(recursive != null && recursive);
		return minioClient.listObjects(builder.build());
	}

	/**
	 * 获取文件外链
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param expires 过期时间
	 * @return url
	 * @throws Exception 异常
	 */
	public String getFileSignUrl(String bucketName, String objectName, Integer expires) throws Exception{
		GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(bucketName)
				.object(objectName).method(Method.GET).expiry(expires, TimeUnit.DAYS).build();
		return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
	}

	/**
	 * 获取文件
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @return 文件流
	 * @throws Exception 异常
	 */
	public InputStream getObject(String bucketName, String objectName)  throws Exception{
		GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
		return minioClient.getObject(args);
	}

	/**
	 * 上传文件
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @param stream 文件流
	 * @param contentType 内容类型
	 * @throws Exception 异常
	 */
	public void putObject(String bucketName, String objectName, InputStream stream, String contentType) throws Exception {
		PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(objectName).contentType(contentType).stream(stream,stream.available(),-1).build();
		minioClient.putObject(args);
	}

	/**
	 * 删除文件
	 * @param bucketName bucket名称
	 * @param objectName 文件名称
	 * @throws Exception 异常
	 */
	public void removeObject(String bucketName, String objectName) throws Exception {
		RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
		minioClient.removeObject(removeObjectArgs);
	}
}
