public class NotifyChange {

    String recentValue = "";
    String lastValue = "";

    public static void main(String args[]){
        new NotifyChange().invoke();
    }

    public void invoke(){

        while(true)
        {
            try
            {
                Thread.sleep(1000);
                DynamicPropertyReader dr = new DynamicPropertyReader();
                dr.notifyChanges();
                recentValue = dr.getProperty("spring.datasource.url");
                if(!lastValue.equals(recentValue)){
                    System.out.println("Yes..something changed: "+recentValue);
                    lastValue = recentValue;
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }

    }
}
