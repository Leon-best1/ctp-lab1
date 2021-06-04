import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

class Crawler implements Runnable{

    private HashMap<String, URLDepthPair> links = new HashMap<>();
    private LinkedList<URLDepthPair> pool = new LinkedList<>();
    
    private int depth = 0;
    
    public Crawler(String url, int depth_) {
        depth = depth_;
        pool.add(new URLDepthPair(url, 0));
    }
    
    public void run() 
    {
        while (pool.size() > 0 || Thread.activeCount() > 1) 
        {
            if (pool.size() > 0) 
            {
                URLDepthPair link = pool.pop();
                CrawlerThread task = new CrawlerThread(link);
                task.start();
           }
        }
        
        for (URLDepthPair link : links.values())
            System.out.println(link);
            
        System.out.println();
        System.out.printf("Found %d URLS\n", links.size());
    }
    
    public static Pattern LINK_REGEX = Pattern.compile(
        "<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1"
    );
    
    private class CrawlerThread extends Thread 
    {
        private URLDepthPair link;

        public CrawlerThread(URLDepthPair link_) 
        {
            link = link_;
        }


        public void run() 
        {
            if (links.containsKey(link.getURL())) {
                URLDepthPair knownLink = links.get(link.getURL());
                knownLink.incrementVisited();
                return;
            }
            
            links.put(link.getURL(), link);
            
            if (link.getDepth() >= depth)
                return;
            
            try {
                URL url = new URL(link.getURL());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                
                Scanner s = new Scanner(con.getInputStream());
                while (s.findWithinHorizon(LINK_REGEX, 0) != null) {
                    String newURL = s.match().group(2);
                    if (newURL.startsWith("/"))
                        newURL = link.getURL() + newURL;
                    else if (!newURL.startsWith("http"))
                        continue;
                    URLDepthPair newLink = new URLDepthPair(newURL, link.getDepth() + 1);
                    pool.add(newLink);
                }
            } catch (Exception e) {}
        }
    }

    public static void showHelp() {
        System.out.println("usage: java Crawler <URL> <depth>");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length != 2) showHelp();
        
        String url = args[0];
        int depth = 0;
        
        try {
            depth = Integer.parseInt(args[1]);
        } catch (Exception e) {
            showHelp();
        }
        
        Crawler crawler = new Crawler(url, depth);
        crawler.run();
    }
}