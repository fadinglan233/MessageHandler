package mysql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fadinglan on 2017/5/6.
 */
public class DeviceInfo {

    private static final Log logger = LogFactory.getLog(DeviceInfo.class);

    public static boolean updateDevice(String deviceId, String sleepData){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        String sql = "update sleep_data set mt_heart_rate ='" + sleepData + "' WHERE vc_device_id = '" + deviceId + "'";
        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.commit();
            logger.debug("device :[" + deviceId + "] save successful" );
            return true;
        }catch (SQLException e){
            logger.error("update error" , e);
        }finally {
            DBPool.release(con, ps, null);
        }
        return false;

    }

    public static boolean deleteDevice(String deviceId){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

//        String sql = "DELETE from sleep_data where vc_device_id = '" + deviceId + "' and t_start_time = '"
//                + ServerMain.dataMap.get(deviceId) + "''";
        String sql = "DELETE from sleep_data where vc_device_id = '" + deviceId;

        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.commit();
            logger.debug("delete sleepData for device :[" + deviceId + "]");
            return true;
        }catch (SQLException e){
            logger.error("delete error" , e);
        }finally {
            DBPool.release(con, ps, null);
        }
        return false;

    }
}
