import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import mydata.GoalsEntity;

import java.util.List;
import java.util.Map;
import org.json.*;


// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void removeAllInstances() {

        Session session = getSession();
        session.beginTransaction();
        session.createQuery("delete GoalsEntity ").executeUpdate();
        session.getTransaction().commit();
    }


    public Integer addGoal(int id, String goalString){
        final Session session = getSession();
        Transaction tx = null;
        Integer goalID = null;
        try{
            tx = session.beginTransaction();
            GoalsEntity goal = new GoalsEntity();
            goal.setId(id);
            goal.setGoal(goalString);
            goalID = (Integer) session.save(goal);
            tx.commit();
        }catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return goalID;
    }

    ArrayList<String> ar = new ArrayList<String>();
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces(MediaType.APPLICATION_JSON)
    public String getClichedMessage() {
        final Session session = getSession();
        List<GoalsEntity> list = session.createQuery("from GoalsEntity ").list();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i=0;i<list.size();i++)
        {
            array.put(list.get(i).getGoal().toString());
        }
        json.put("goals",array);

        return json.toString();
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDataInJSON(String data) {
        removeAllInstances();
        JSONObject obj = new JSONObject(data);

        JSONArray arr = obj.getJSONArray("goals");
        for (int i = 0; i < arr.length(); i++)
        {
            String myGoal = arr.getString(i);
            addGoal(i,myGoal);
        }

        return Response.status(201).entity(data).build();
    }

}