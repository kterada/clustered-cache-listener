/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other
 * contributors as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.infinispan.quickstart.clusteredcache.distribution;

import org.infinispan.Cache;
import org.infinispan.quickstart.clusteredcache.util.LoggingListener;
// add
import org.infinispan.manager.EmbeddedCacheManager;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.concurrent.Future;
import org.infinispan.util.concurrent.FutureListener;
// add end
public class Node2 extends AbstractNode {

   public static void main(String[] args) throws Exception {
      new Node2().run();
   }

   public void run() {
   	EmbeddedCacheManager cm = getCacheManager();
      Cache<String, String> cache = cm.getCache("Demo");

      waitForClusterToForm();

      // Add a listener so that we can see the puts to this node
      cache.addListener(new LoggingListener());
      
   	  // load check
      for (int i = 0; i < 20; i++)
   	    System.out.println("load check key=key" + i + "  value=" + cache.get("key" + i));

   	// Put a few entries into the cache so that we can see them distribution to the other nodes
      for (int i = 0; i < 20; i++)
         cache.put("key" + i, "value" + i);
   	System.out.println("key1 value = " + cache.get("key1"));
   	cache.remove("key1");
   	String key2_value = cache.get("key2");
   	cache.put("key2",key2_value + " modifyvalue");
   	
    cache.put("key30", "key30 10sec lifespan", 5, SECONDS);
   	if(cache.containsKey("key30")){
   		System.out.println("key30 is exist");
   	}else{
   		System.out.println("key30 is not exist");
   	}
   	if(cache.containsKey("key30")){
   		System.out.println("key30 is exist");
   	}else{
   		System.out.println("key30 is not exist");
   	}
   	cache.stop();
   	cache.start();
   	
   }
   
   @Override
   protected int getNodeId() {
      return 2;
   }

}
