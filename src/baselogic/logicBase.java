package baselogic;
/**
 *
 * @author yuki shishikura
 *	処理設定クラス
 *
 */

import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author yuki shishikura
 *	処理設定クラス
 *
 */

public class logicBase {

	/**
	 * コンストラクタ
	 */
	public logicBase() {

		//初期処理の実行
		init();
	}

	/**
	 * 初期処理
	 */
	public void init() {
		//log設定読み込み処理
		PropertyConfigurator.configure("conf/log4j.properties");

		//初期処理の
		init();
	}



}
