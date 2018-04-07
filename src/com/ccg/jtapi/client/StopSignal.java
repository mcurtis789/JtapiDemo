package com.ccg.jtapi.client;
/*   
*  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
*  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
*  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JCRAFT,
*  INC. OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
*  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
*  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
*  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
*  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
*  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
*  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/////////////////////////////////////////////////////////////////////
//
// File:                StopSignal.java
//
// Description:  The code in this file has been implemented to help catch system level interrupts. 
//	
//
// Revision:    1.0
// Created:     Oct 14, 2003
// Author:      Vasu Ramachandra
// Email:       varamach@cisco.com
//
// Copyright (c) 2001 Cisco Systems Inc.  All rights reserved.
//
// The copyright to the computer program(s) herein is the property of
// Cisco Systems Inc. The program(s) may be used and/or copied only with
// the written permission of Cisco Systems Inc. or in accordance with the terms
// and conditions stipulated in the agreement/contract under which
// the program(s) has been supplied
//
//
////////////////////////////////////////////////////////////////////////


public class StopSignal {
	boolean stopping = false;
	boolean stopped = false;
	synchronized boolean isStopped () {
		return stopped;
	}
	synchronized boolean isStopping () {
		return stopping;
	}
	public synchronized void stop () {
		if ( !stopped ) {
			stopping = true;
			try {
				wait ();
			}
			catch ( InterruptedException e ) {}
		}
	}
	synchronized void canStop () {
		if ( stopping = true ) {
			stopping = false;
			stopped = true;
			notify ();
		}
	}
}

