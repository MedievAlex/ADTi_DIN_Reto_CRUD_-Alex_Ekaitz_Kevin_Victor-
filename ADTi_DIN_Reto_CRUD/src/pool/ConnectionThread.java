package pool;

import exception.OurException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionThread extends Thread
{
    private int delay = 30;
    private boolean end = false;
    private boolean ready = false;
    private Connection con;
    private OurException exception;

    public ConnectionThread(int delay)
    {
        this.delay = delay;
    }

    public Connection getConnection() throws OurException
    {
        if (exception != null) {
            throw exception;
        }

        return con;
    }

    public boolean isReady()
    {
        return ready;
    }

    public void releaseConnection()
    {
        this.end = true;
        this.interrupt();
    }

    @Override
    public void run()
    {
        try
        {
            con = ConnectionPool.getConnection();
            ready = true;

            while (!end)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    if (end)
                    {
                        break;
                    }

                    Thread.currentThread().interrupt();
                }
            }

            try
            {
                Thread.sleep(delay * 1000);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
        catch (SQLException ex)
        {
            exception = new OurException("Error obtaining a connection from pool: " + ex.getMessage());
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (SQLException ex)
            {
                exception = new OurException("Error returning the connection: " + ex.getMessage());
            }
        }
    }
}
