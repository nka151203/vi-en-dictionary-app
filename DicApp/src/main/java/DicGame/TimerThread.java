package DicGame;

public class TimerThread extends Thread {
    private int timeLeft;  // Thời gian còn lại tính bằng giây
    private boolean running;

    public TimerThread(int initialTime) {
        this.timeLeft = initialTime;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(1000);  // Tạm dừng 1 giây (1000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLeft--;

            if (timeLeft <= 0) {
                running = false;
                // Xử lý sự kiện hết thời gian ở đây
            }
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void stopTimer() {
        running = false;
    }
}

