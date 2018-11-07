package util;

/**
 * To be thrown when segmentation error occurs.
 */
public class SegmentationException extends ExecutionException {
	static final protected long serialVersionUID = 43L;

	public SegmentationException(String msg) {
		super("Segmentation : " + msg);
	}
}
