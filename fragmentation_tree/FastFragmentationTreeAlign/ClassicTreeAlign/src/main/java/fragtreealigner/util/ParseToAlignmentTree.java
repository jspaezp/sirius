/*
 *  This file is part of the SIRIUS library for analyzing MS and MS/MS data
 *
 *  Copyright (C) 2013-2015 Kai Dührkop
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with SIRIUS.  If not, see <http://www.gnu.org/licenses/>.
 */
package fragtreealigner.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: fhufsky
 * Date: 8/2/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseToAlignmentTree {

    public static void main(String[] args) {


        File alignmentFile   =   new File("/Users/fhufsky/Uni/Projects/TreeAlignment/scripts/output/alignment.dot");



        try {
            BufferedReader reader = new BufferedReader(new FileReader(alignmentFile));

            Map<String,String> nodesTree1 = new HashMap<String, String>();
            Map<String,String> nodesTree2 = new HashMap<String, String>();

            Map<String,String> edgesTree1 = new HashMap<String, String>();
            Map<String,String> edgesTree2 = new HashMap<String, String>();



            for (String line = reader.readLine(); line!=null; line=reader.readLine()){
                if (line.contains("sg0")){ //tree1

                    while (!line.contains("}")){

                        if (line.contains("[")){
                            String label = line.split("=")[1].split("\"")[1];
                            //System.out.println(label);
                            String name = line.split(" ")[0];
                            if (label.contains("[")){

                                String node = label.split("\\[")[1].split(" ")[0];
                                System.out.println(node);
                                String edge = label.split(" ")[1].split("\\]")[0];
                                System.out.println(edge);

                                nodesTree1.put(name,node);
                                edgesTree1.put(name,edge);


                            }


                        }

                        line =reader.readLine();
                    }

                }



            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
