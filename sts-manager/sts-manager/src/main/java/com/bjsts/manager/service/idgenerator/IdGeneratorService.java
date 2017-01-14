package com.bjsts.manager.service.idgenerator;


import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.idgenerator.IdGeneratorEntity;

/**
 * 发号器
 * !!!用到发号器的地方必须在事务外部
 * @author Sunshow
 *
 */
public interface IdGeneratorService extends IService<IdGeneratorEntity, String> {

	/**
	 * 获取1个号
	 * @param sequence 序列名称
	 * @return 第一个号的值
	 */
	Long generate(String sequence);

	/**
	 * 获取多个号
	 * @param sequence 序列名称
	 * @param count 发号个数
	 * @return 第一个号的值
	 */
	Long generate(String sequence, int count);

	/**
	 * 获取多个号
	 * @param sequence 序列名称
	 * @param count 发号个数
	 * @param groupKey 指定分组时按分组递增, 一旦分组发生变化立即复位计数器
	 * @return 第一个号的值
	 */
	Long generateWithGroup(String sequence, int count, String groupKey);

    /**
     * 获取一个序列的自增值，形式如：年月日(yyMMdd)+000(3位预留码)+100001(6位数字)
     * @param sequence 序列名称
     * @return 第一个号的值
     */
	String generateDateFormatted(String sequence);

    /**
     * 获取一个序列的自增值，形式如：年月日(yyMMdd)+000(3位预留码)+100001(6位数字)
     * @param sequence 序列名称
     * @param count 发号个数
     * @return 第一个号的值
     */
	String generateDateFormatted(String sequence, int count);

    /**
     * 获取一个序列的自增值，形式如：年月日(yyMMdd)+000(3位预留码)+前缀码(1位)+00001(5位数字)
     * @param sequence 序列名称
     * @param count 发号个数
     * @param prefix 前缀码
     * @return 第一个号的值
     */
    String generateDateFormatted(String sequence, int count, int prefix);
}
