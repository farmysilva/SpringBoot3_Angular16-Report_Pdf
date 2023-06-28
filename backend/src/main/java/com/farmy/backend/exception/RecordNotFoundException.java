package com.farmy.backend.exception;

public class RecordNotFoundException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException( Long id) {
        super("Could not find record " + id);
    }
}
