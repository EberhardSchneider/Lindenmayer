/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lindenmayr;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import java.util.AbstractMap.SimpleEntry;

/**
 *
 * @author eberh_000
 */
public class Rules {

    private static Object AbstractMap;
    
    
    Map<Character, String> r = new HashMap<>();
    ObservableMap<Character, String> rules = FXCollections.observableMap( r );
    
    public Rules() {
        
    }
    
    public void addRule( Character p, String s ) {
        if (rules.containsKey( p )) {
            rules.remove( p );
        }
        rules.put( p , s);
    }
    
    public void removeRule( Character p ) {
        rules.remove( p );
    }
    
    public String getRulesAsString() {
        StringBuilder sb = new StringBuilder();
        for ( Map.Entry<Character, String> entry : rules.entrySet() ) {
            sb.append( entry.getKey() );
            sb.append( " -> ");
            sb.append( entry.getValue() );
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public ObservableMap<Character, String> getRules() {
        return rules;
    }
    
    public String applyRules( String s ) {
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            String result = rules.get( s.charAt( i ) );
            if ( result == null ) {
                sb.append( s.charAt(i) );
            } else {
                sb.append( result );
            }
        }
        
       
        
        //return optimizedString( sb.toString() );
        return sb.toString();
    }
    
    public void deleteRule( Character p) {
        rules.remove( p );
    }
    
    /**
     * deletes all instances of '+-' or '-+'
     * @param s
     * @return 
     */
    private String optimizedString( String s ) {
        
        StringBuilder sb = null;
        boolean hasChanged = true;
        
        while (hasChanged) {
           
            sb = new StringBuilder();
            hasChanged = false;
            
            for (int i = 0; i < s.length()-1; i++) {
                if ( (s.charAt(i) == '+') && (s.charAt(i+1) == '-') ) {
                    i++;
                    hasChanged = true;
                } else if ((s.charAt(i) == '-') && (s.charAt(i+1) == '+') ) {
                    i++;
                    hasChanged = true;
                } else {
                    sb.append( s.charAt( i ));
                }
            }
            
            s = sb.toString();
        }
        
        return sb.toString();
    }

    public void addListener(MapChangeListener listener) {
        rules.addListener( listener );
    }
    
    public void addRuleFromString( String line ) {
        
        SimpleEntry<Character, String> parsedRule;

        if (line.length() < 4)
            return;

        Character key = line.charAt( 0 );

        if ( key < 'A' || key > 'z') {
            return;
        }

        if ( !(line.substring(1,3)).equals("->")) {
            return;
        }

        String value = line.substring(3);

        addRule( key, value );
        
    }

    
}
