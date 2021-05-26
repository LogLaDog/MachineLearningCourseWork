package test;
public interface KNNHandler {
    public void setDistanceMetric(metric metric);
    public void K(int k);
    public void train(Set training_set);
    public double[] test(Set testing_set);
}