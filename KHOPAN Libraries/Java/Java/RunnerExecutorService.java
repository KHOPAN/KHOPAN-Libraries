ScheduledExecutorService ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
				ScheduledExecutorService.scheduleAtFixedRate(new Runnable()
				{
					@Override
					public void run()
					{
						
					}
				}, 0, 0, TimeUnit.MICROSECONDS);