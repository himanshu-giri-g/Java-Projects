class TrafficLight {
    private boolean green = false;

    public synchronized void turnGreen() {
        green = true;
        notifyAll();
    }

    public synchronized void turnRed() {
        green = false;
    }

    public synchronized void waitForGreen() throws InterruptedException {
        while (!green) {
            wait();
        }
    }
}

class Car extends Thread {
    private TrafficLight trafficLight;

    public Car(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void run() {
        try {
            trafficLight.waitForGreen();
            System.out.println(Thread.currentThread().getName() + " is passing through the intersection.");
        } catch (InterruptedException e) {
        }
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }
}

public class TrafficLightSystem {
    public static void main(String[] args) throws InterruptedException {
        TrafficLight trafficLight = new TrafficLight();

        for (int i = 0; i < 5; i++) {
            new Car(trafficLight).start();
        }

        // Simulate traffic light changes
        Thread.sleep(2000);
        trafficLight.turnGreen();
        Thread.sleep(2000);
        trafficLight.turnRed();
    }
}
