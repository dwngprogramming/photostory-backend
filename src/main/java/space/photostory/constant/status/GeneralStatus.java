package space.photostory.constant.status;

public enum GeneralStatus implements BaseStatus {
    ACTIVE,
    INACTIVE;

    @Override
    public boolean isActive() {
        return this == ACTIVE;
    }

    @Override
    public boolean isInactive() {
        return this == INACTIVE;
    }
}
