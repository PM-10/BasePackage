package kr.pm10.basepackage.library.otto;

public class BusProvider {
    private static MainThreadBus bus;

    public static MainThreadBus getInstance() {
        if (bus == null)
            bus = new MainThreadBus();
        return bus;
    }
}
