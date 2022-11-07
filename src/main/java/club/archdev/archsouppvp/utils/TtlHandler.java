package club.archdev.archsouppvp.utils;

public interface TtlHandler<E> {

    void onExpire(E element);

    long getTimestamp(E element);
}
