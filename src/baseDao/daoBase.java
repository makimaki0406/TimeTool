package baseDao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import constant.Constant;

/**
 *
 * @author yuki shishikura daoクラスベース処理
 *
 */
public class daoBase {
	Logger logger = Logger.getLogger(daoBase.class.getName());

	daoBase() {
		getDbInfo();
	}

	/**
	 *
	 * db情報の取得
	 *
	 */
	private void getDbInfo() {

		// Propertiesの読み込み
		Properties properties = new Properties();

		try {
			// ファイルパスの取得
			InputStream istream = new FileInputStream(Constant.STRPASS);
			// 読み込み
			properties.load(istream);

			// ホストネームの取得
			Constant.HOST_NAME = properties.getProperty("host");

			// DB名の取得
			Constant.DB_NAME = properties.getProperty("db");

			// DBパスワードの取得
			Constant.DB_PASSWORD = properties.getProperty("password");

			// ドライバー名の取得
			Constant.JDBC_DRIVER_NAME = properties.getProperty("driver");

		} catch (IOException e) {
			logger.error("【データベース定義エラー】" + e);
		}
	}

	/**
	 * トランザクションを開始する
	 */
	protected void startTransaction() {
		try {
			Class.forName(Constant.JDBC_DRIVER_NAME);
			Constant.conn = DriverManager.getConnection(Constant.JDBC_DRIVER_NAME+ Constant.HOST_NAME, Constant.DB_NAME, Constant.DB_PASSWORD);

		} catch (Exception e) {
			logger.error("【DB接続エラー】" + e);
		}
	}

	/**
	 * コミットを実行し、トランザクションを終了する
	 */
	protected void commit() {
		try {
			if (Constant.conn != null) {
				Constant.conn.commit();
			}
		} catch (SQLException e) {
			logger.error("【DBコミットエラー】" + e);
		} finally {
			close();
		}
	}

	/**
	 * ロールバックを実行し、トランザクションを終了する
	 */
	protected void rollback() {
		try {
			if (Constant.conn != null) {
				Constant.conn.rollback();
			}
		} catch (SQLException e) {
			logger.error("【DBロールバックエラー】" + e);
		} finally {
			close();
		}
	}

	/**
	 * DBコネクションをクローズする
	 */
	protected void close() {
		if (Constant.conn != null) {
			try {
				Constant.conn.close();
			} catch (SQLException e) {
				logger.error("【Connectionクローズエラー】" + e);
			}
		}
	}

	/**
	 * Statementをクローズする
	 *
	 * @param stmt
	 *            Statement
	 */
	protected void close(Statement stmt) {
		if (stmt != null) {

			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("【Statementクローズエラー】" + e);
			}
		}
	}

	/**
	 * PreparedStatementをクローズする
	 *
	 * @param ps
	 *            PreparedStatement
	 */
	protected void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("【PreparedStatementクローズエラー】" + e);
			}
		}
	}

	/**
	 * ResultSetをクローズする
	 *
	 * @param rs
	 *            ResultSet
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("【ResultSetクローズエラー】" + e);
			}
		}
	}
}
