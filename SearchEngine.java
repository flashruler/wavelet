import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> queries = new ArrayList<String>();
    
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Welcome to this scuffed search engine");
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) { 
                queries.add(parameters[1]);
                return String.format("test: " + queries.get(queries.size()-1));
            }
            return String.format("else try again");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) { 
                String query = parameters[1];
                ArrayList<String> values = new ArrayList<String>();
                for(int x=0;x<queries.size();x++){
                    if(queries.get(x).contains(query)){
                        values.add(queries.get(x));
                    }
                }
                return String.format("test: " + values.toString());
            }
            }

            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
