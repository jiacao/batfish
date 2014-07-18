parser grammar CiscoGrammar_acl;

import CiscoGrammarCommonParser;

options {
	tokenVocab = CiscoGrammarCommonLexer;
}

access_list_ip_range
:
	(
		ip = IP_ADDRESS wildcard = IP_ADDRESS
	)
	| ANY
	| HOST ip = IP_ADDRESS
;

appletalk_access_list_numbered_stanza
locals [boolean again]
:
	ACCESS_LIST name = ACL_NUM_APPLETALK appletalk_access_list_null_tail
	{
		$again = _input.LT(1).getType() == ACCESS_LIST &&
		_input.LT(2).getType() == ACL_NUM_APPLETALK &&
		_input.LT(2).getText().equals($name.text);
	}

	(
		{$again}?

		appletalk_access_list_numbered_stanza
		|
		{!$again}?

	)
;

appletalk_access_list_null_tail
:
	action = access_list_action
	(
		(
			CABLE_RANGE ~NEWLINE*
		)
		| OTHER_ACCESS
	)? NEWLINE
;

appletalk_access_list_stanza
:
	numbered = appletalk_access_list_numbered_stanza
;

extended_access_list_named_stanza
:
	IP ACCESS_LIST EXTENDED name = ~NEWLINE NEWLINE
	(
		extended_access_list_tail
		| extended_access_list_null_tail
	)*
;

extended_access_list_null_tail
:
	(
		(
			access_list_action protocol access_list_ip_range port_specifier?
			access_list_ip_range port_specifier? REFLECT
		)
		| DYNAMIC
		| EVALUATE
		| REMARK
	) ~NEWLINE* NEWLINE
;

extended_access_list_numbered_stanza
locals [boolean again]
:
	ACCESS_LIST name = ACL_NUM_EXTENDED
	(
		extended_access_list_tail
		| extended_access_list_null_tail
	)
	{
		$again = _input.LT(1).getType() == ACCESS_LIST &&
		_input.LT(2).getType() == ACL_NUM_EXTENDED &&
		_input.LT(2).getText().equals($name.text);
	}

	(
		{$again}?

		extended_access_list_numbered_stanza
		|
		{!$again}?

	)
;

extended_access_list_stanza
:
	named = extended_access_list_named_stanza
	| numbered = extended_access_list_numbered_stanza
;

extended_access_list_tail
:
	ala = access_list_action prot = protocol srcipr = access_list_ip_range
	(
		alps_src = port_specifier
	)? dstipr = access_list_ip_range
	(
		alps_dst = port_specifier
	)?
	(
		ECHO_REPLY
		| ECHO
		| ESTABLISHED
		| FRAGMENTS
		| LOG
		| LOG_INPUT
		| PACKET_TOO_BIG
		| PARAMETER_PROBLEM
		| PORT_UNREACHABLE
		| REDIRECT
		| TIME_EXCEEDED
		| TTL_EXCEEDED
		| UNREACHABLE
	)? NEWLINE
;

ip_as_path_access_list_stanza
:
	numbered = ip_as_path_numbered_stanza
;

ip_as_path_numbered_stanza
locals [boolean again]
:
	IP AS_PATH ACCESS_LIST name = . ip_as_path_access_list_tail
	{
		$again = _input.LT(1).getType() == IP &&
		_input.LT(2).getType() == AS_PATH &&
		_input.LT(3).getType() == ACCESS_LIST &&
		_input.LT(4).getText().equals($name.text);
	}

	(
		{$again}?

		ip_as_path_numbered_stanza
		|
		{!$again}?

	)
;

ip_as_path_access_list_tail
:
	action = access_list_action
	(
		remainder += ~NEWLINE
	)+ NEWLINE
;

ip_community_list_expanded_stanza
:
	named = ip_community_list_expanded_named_stanza
	| numbered = ip_community_list_expanded_numbered_stanza
;

ip_community_list_expanded_named_stanza
locals [boolean again]
:
	IP COMMUNITY_LIST EXPANDED name = VARIABLE ip_community_list_expanded_tail
	{
		$again = _input.LT(1).getType() == IP &&
		_input.LT(2).getType() == COMMUNITY_LIST &&
		_input.LT(3).getType() == EXPANDED &&
		_input.LT(4).getType() == VARIABLE &&
		_input.LT(4).getText().equals($name.text);
	}

	(
		{$again}?

		ip_community_list_expanded_named_stanza
		|
		{!$again}?

	)
;

ip_community_list_expanded_numbered_stanza
locals [boolean again]
:
	IP COMMUNITY_LIST name = COMMUNITY_LIST_NUM_EXPANDED
	ip_community_list_expanded_tail
	{
		$again = _input.LT(1).getType() == IP &&
		_input.LT(2).getType() == COMMUNITY_LIST &&
		_input.LT(3).getType() == COMMUNITY_LIST_NUM_EXPANDED &&
		_input.LT(3).getText().equals($name.text);
	}

	(
		{$again}?

		ip_community_list_expanded_numbered_stanza
		|
		{!$again}?

	)
;

ip_community_list_expanded_tail
:
	ala = access_list_action
	(
		remainder += ~NEWLINE
	)+ NEWLINE
;

ip_community_list_standard_stanza
:
	named = ip_community_list_standard_named_stanza
	| numbered = ip_community_list_standard_numbered_stanza
;

ip_community_list_standard_named_stanza
locals [boolean again]
:
	IP COMMUNITY_LIST STANDARD name = VARIABLE ip_community_list_standard_tail
	{
		$again = _input.LT(1).getType() == IP &&
		_input.LT(2).getType() == COMMUNITY_LIST &&
		_input.LT(3).getType() == STANDARD &&
		_input.LT(4).getType() == VARIABLE &&
		_input.LT(4).getText().equals($name.text);
	}

	(
		{$again}?

		ip_community_list_standard_named_stanza
		|
		{!$again}?

	)
;

ip_community_list_standard_numbered_stanza
locals [boolean again]
:
	IP COMMUNITY_LIST name = COMMUNITY_LIST_NUM_STANDARD
	ip_community_list_standard_tail
	{
		$again = _input.LT(1).getType() == IP &&
		_input.LT(2).getType() == COMMUNITY_LIST &&
		_input.LT(3).getType() == COMMUNITY_LIST_NUM_STANDARD &&
		_input.LT(3).getText().equals($name.text);
	}

	(
		{$again}?

		ip_community_list_standard_numbered_stanza
		|
		{!$again}?

	)
;

ip_community_list_standard_tail
:
	ala = access_list_action
	(
		communities += community
	)+ NEWLINE
;

ip_prefix_list_stanza
:
	named = ip_prefix_list_named_stanza
;

ip_prefix_list_named_stanza
locals [boolean again]
:
	IP PREFIX_LIST name = VARIABLE ip_prefix_list_tail
	{
		$again = _input.LT(1).getType() == IP &&
		_input.LT(2).getType() == PREFIX_LIST &&
		_input.LT(3).getType() == VARIABLE &&
		_input.LT(3).getText().equals($name.text);
	}

	(
		{$again}?

		ip_prefix_list_named_stanza
		|
		{!$again}?

	)
;

ip_prefix_list_tail
:
	(
		SEQ seqnum = DEC
	)? action = access_list_action prefix = IP_ADDRESS FORWARD_SLASH prefix_length
	= DEC
	(
		(
			GE minpl = DEC
		)
		|
		(
			LE maxpl = DEC
		)
	)* NEWLINE
;

ipx_sap_access_list_numbered_stanza
locals [boolean again]
:
	ACCESS_LIST name = ACL_NUM_IPX_SAP ipx_sap_access_list_null_tail
	{
		$again = _input.LT(1).getType() == ACCESS_LIST &&
		_input.LT(2).getType() == ACL_NUM_IPX_SAP &&
		_input.LT(2).getText().equals($name.text);
	}

	(
		{$again}?

		ipx_sap_access_list_numbered_stanza
		|
		{!$again}?

	)
;

ipx_sap_access_list_null_tail
:
	action = access_list_action ~NEWLINE* NEWLINE
;

ipx_sap_access_list_stanza
:
	numbered = ipx_sap_access_list_numbered_stanza
;

protocol_type_code_access_list_numbered_stanza
locals [boolean again]
:
	ACCESS_LIST name = ACL_NUM_PROTOCOL_TYPE_CODE
	protocol_type_code_access_list_null_tail
	{
		$again = _input.LT(1).getType() == ACCESS_LIST &&
		_input.LT(2).getType() == ACL_NUM_PROTOCOL_TYPE_CODE &&
		_input.LT(2).getText().equals($name.text);
	}

	(
		{$again}?

		protocol_type_code_access_list_numbered_stanza
		|
		{!$again}?

	)
;

protocol_type_code_access_list_null_tail
:
	action = access_list_action ~NEWLINE* NEWLINE
;

protocol_type_code_access_list_stanza
:
	numbered = protocol_type_code_access_list_numbered_stanza
;

standard_access_list_null_tail
:
	(
		REMARK ~NEWLINE* NEWLINE
	)
	|
	(
		ala = access_list_action ipr = IP_ADDRESS LOG? NEWLINE
	)
;

standard_access_list_named_stanza
:
	IP ACCESS_LIST STANDARD name = ~NEWLINE NEWLINE
	(
		standard_access_list_tail
		| standard_access_list_null_tail
	)*
;

standard_access_list_numbered_stanza
locals [boolean again]
:
	ACCESS_LIST name = ACL_NUM_STANDARD
	(
		standard_access_list_tail
		| standard_access_list_null_tail
	)
	{
		$again = _input.LT(1).getType() == ACCESS_LIST &&
		_input.LT(2).getType() == ACL_NUM_STANDARD &&
		_input.LT(2).getText().equals($name.text);
	}

	(
		{$again}?

		standard_access_list_numbered_stanza
		|
		{!$again}?

	)
;

standard_access_list_stanza
:
	named = standard_access_list_named_stanza
	| numbered = standard_access_list_numbered_stanza
;

standard_access_list_tail
:
	ala = access_list_action ipr = access_list_ip_range LOG? NEWLINE
;
