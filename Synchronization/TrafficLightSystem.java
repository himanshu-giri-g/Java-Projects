class TrafficLight {
    private boolean green = false;

    public synchronized void turnGreen() {
        green = true;
        System.out.println("Traffic light is GREEN.");
        notifyAll(); // Notify waiting cars that they can go
    }

    public synchronized void turnRed() {
        green = false;
        System.out.println("Traffic light is RED.");
    }

    public synchronized void waitForGreen() throws InterruptedException {
        while (!green) {
            wait(); // Wait until the light turns green
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
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
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

        // Start multiple car threads
        for (int i = 0; i < 5; i++) {
            new Car(trafficLight).start();
        }

        // Simulate traffic light changes
        Thread.sleep(2000);
        trafficLight.turnGreen();
        Thread.sleep(5000); // Keep the light green for 5 seconds
        trafficLight.turnRed();
    }
}
