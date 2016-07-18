package com.boce.test.内存分析;

import java.util.concurrent.atomic.AtomicInteger;

public class Finalizable {
	 static AtomicInteger aliveCount = new AtomicInteger(0);

     Finalizable() {
           aliveCount.incrementAndGet();
    }

    @Override
    protected void finalize() throws Throwable {
                 Finalizable.aliveCount.decrementAndGet();
    }

     public static void main(String args[]) {
           for (int i = 0;; i++) {
                 Finalizable f = new Finalizable();
                 if ((i % 100000) == 0) {
                       System.out.format("After creating %d objects, %d are still alive.%n", new Object[] {i, Finalizable.aliveCount.get() });
                   }
         }
    }
}
