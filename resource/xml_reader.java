package resource;

import data_structure.GraphAdjList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import plugin.View_Point;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class xml_reader {
    static GraphAdjList<View_Point> map = new GraphAdjList<>(1000);
    StringBuilder result = new StringBuilder();
    static int reader_counter; //0:name, 1:start, 2:end, 3:weight, 4:edge
    static int Vex_start;
    static int Vex_end;
    static int Vex_weight;
    static class MyDefaultHandler extends DefaultHandler {
        @Override
        public void startElement(String url, String localName, String qName,
                                 Attributes attributes) {
            //System.out.print("<" + qName + ">");
            switch(qName) {
                case "name" -> reader_counter = 0;
                case "start" -> reader_counter = 1;
                case "end" -> reader_counter = 2;
                case "weight" -> reader_counter = 3;
                case "edge" -> reader_counter = 4;
                default -> reader_counter = -1;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if(reader_counter == 0) {
                View_Point node = new View_Point(new String(ch, start, length));
                map.insertVex(node);
            } else if(reader_counter == 1) {
                String ttmp = new String(ch, start, length);
                Vex_start = Integer.parseInt(ttmp);
            } else if(reader_counter == 2) {
                String ttmp = new String(ch, start, length);
                Vex_end = Integer.parseInt(ttmp);
            } else if(reader_counter == 3) {
                String ttmp = new String(ch, start, length);
                Vex_weight = Integer.parseInt(ttmp);
            }
        }

        @Override
        public void endElement(String url, String localName, String qName) {
            if(qName.equals("edge")) map.insertEdge(Vex_start, Vex_end, Vex_weight);
            else reader_counter = -1;
        }
    }
    public static GraphAdjList<View_Point> read_xml() {
        reader_counter = 0;
        try {
            SAXParserFactory SAXParserFactory = javax.xml.parsers.SAXParserFactory.newInstance();
            SAXParser saxParser = SAXParserFactory.newSAXParser();
            saxParser.parse("src/resource/resource.xml", new MyDefaultHandler());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
