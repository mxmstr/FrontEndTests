


class AsyncTester {
	
    private Thread thread;
    private AssertionError exc; 

    public AsyncTester(final Runnable runnable) {
        thread = new Thread(new Runnable() {
            public void run() {
                try{            
                    runnable.run();
                }catch(AssertionError e) {
                    exc = e;
                }
            }
        });
    }

    public void start() {
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
        if (exc != null)
            throw exc;
    }
    
}

