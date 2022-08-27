package com.khopan.list;

import java.io.Serializable;

public class DoubleItemList<A, B> implements Serializable {
	private static final long serialVersionUID = -2252527028263475956L;

	private Object[] A;
	private Object[] B;

	public DoubleItemList() {
		this.A = new Object[0];
		this.B = new Object[0];
	}

	public void add(A A, B B) {
		Object[] NewA = new Object[this.A.length + 1];
		Object[] NewB = new Object[this.A.length + 1];

		for(int i = 0; i < this.A.length; i++) {
			NewA[i] = this.A[i];
			NewB[i] = this.B[i];
		}

		NewA[this.A.length] = A;
		NewB[this.B.length] = B;

		this.A = NewA;
		this.B = NewB;
	}

	public void remove(int Index) {
		Object[] NewA = new Object[this.A.length - 1];
		Object[] NewB = new Object[this.A.length - 1];

		for(int i = 0; i < Index; i++) {
			NewA[i] = this.A[i];
			NewB[i] = this.B[i];
		}

		for(int i = Index + 1; i < this.A.length; i++) {
			NewA[i - 1] = this.A[i];
			NewB[i - 1] = this.B[i];
		}

		this.A = NewA;
		this.B = NewB;
	}

	public boolean has(A A, B B) {
		for(int i = 0; i < this.A.length; i++) {
			if(
					this.A[i].equals(A) &&
					this.B[i].equals(B)
					) {
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public A getA(int Index) {
		return (A) this.A[Index];
	}

	@SuppressWarnings("unchecked")
	public B getB(int Index) {
		return (B) this.B[Index];
	}

	public int size() {
		return (this.A.length + this.B.length) / 2;
	}
}
