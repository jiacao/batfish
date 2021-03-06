lexer grammar CiscoLexer;

options {
   superClass = 'org.batfish.grammar.BatfishLexer';
}

@header {
package org.batfish.grammar.cisco;
}

@members {
int lastTokenType = 0;
boolean enableIPV6_ADDRESS = true;
boolean enableIP_ADDRESS = true;
boolean enableDEC = true;
boolean enableACL_NUM = false;
boolean enableCOMMUNITY_LIST_NUM = false;
boolean inCommunitySet = false;

@Override
public String printStateVariables() {
   StringBuilder sb = new StringBuilder();
   sb.append("enableIPV6_ADDRESS: " + enableIPV6_ADDRESS + "\n");
   sb.append("enableIP_ADDRESS: " + enableIP_ADDRESS + "\n");
   sb.append("enableDEC: " + enableDEC + "\n");
   sb.append("enableACL_NUM: " + enableACL_NUM+ "\n");
   sb.append("enableCOMMUNITY_LIST_NUM: " + enableCOMMUNITY_LIST_NUM + "\n");
   return sb.toString();
}

public void emit(Token token) {
    super.emit(token);
    if (token.getChannel() != HIDDEN) {
       lastTokenType = token.getType();
    }
}
}

tokens {
   ACL_NUM_APPLETALK,
   ACL_NUM_EXTENDED,
   ACL_NUM_EXTENDED_IPX,
   ACL_NUM_IPX,
   ACL_NUM_IPX_SAP,
   ACL_NUM_MAC,
   ACL_NUM_OTHER,
   ACL_NUM_PROTOCOL_TYPE_CODE,
   ACL_NUM_STANDARD,
   CHAIN,
   COMMUNITY_LIST_NUM_EXPANDED,
   COMMUNITY_LIST_NUM_STANDARD,
   HEX_FRAGMENT,
   ISO_ADDRESS,
   PAREN_LEFT_LITERAL,
   PAREN_RIGHT_LITERAL,
   PIPE,
   RAW_TEXT,
   SELF_SIGNED
}

// Cisco Keywords

AAA
:
   'aaa'
;

AAA_SERVER
:
   'aaa-server'
;

ABSOLUTE_TIMEOUT
:
   'absolute-timeout'
;

ACCEPT_DIALIN
:
   'accept-dialin'
;

ACCEPT_LIFETIME
:
   'accept-lifetime'
;

ACCEPT_OWN
:
   'accept-own'
;

ACCEPT_REGISTER
:
   'accept-register'
;

ACCEPT_RP
:
   'accept-rp'
;

ACCESS
:
   'access'
;

ACCESS_CLASS
:
   'access-class'
;

ACCESS_GROUP
:
   'access-group'
;

ACCESS_LIST
:
   'access-list'
   {enableACL_NUM = true; enableDEC = false;}

;

ACCESS_LOG
:
   'access-log'
;

ACCESS_MAP
:
   'access-map'
;

ACCOUNTING
:
   'accounting'
;

ACCOUNTING_LIST
:
   'accounting-list'
;

ACCOUNTING_PORT
:
   'accounting-port'
;

ACCOUNTING_SERVER_GROUP
:
   'accounting-server-group'
;

ACCOUNTING_THRESHOLD
:
   'accounting-threshold'
;

ACK
:
   'ack'
;

ACLLOG
:
   'acllog'
;

ACTION
:
   'action'
;

ACTIVATE
:
   'activate'
;

ACTIVATION_CHARACTER
:
   'activation-character'
;

ACTIVE
:
   'active'
;

ADD
:
   'add'
;

ADDITIVE
:
   'additive'
;

ADDRESS
:
   'address'
;

ADDRESS_FAMILY
:
   'address-family'
;

ADDRESS_POOL
:
   'address-pool'
;

ADDRESS_POOLS
:
   'address-pools'
;

ADDRGROUP
:
   'addrgroup'
;

ADJACENCY
:
   'adjacency'
;

ADMIN
:
   'admin'
;

ADMINISTRATIVE_WEIGHT
:
   'administrative-weight'
;

ADMISSION
:
   'admission'
;

ADVERTISE
:
   'advertise'
;

ADVERTISEMENT_INTERVAL
:
   'advertisement-interval'
;

ADVERTISE_INACTIVE
:
   'advertise-inactive'
;

AES128_SHA1
:
   'aes128-sha1'
;

AES256_SHA1
:
   'aes256-sha1'
;

AESA
:
   'aesa'
;

AF11
:
   'af11'
;

AF12
:
   'af12'
;

AF13
:
   'af13'
;

AF21
:
   'af21'
;

AF22
:
   'af22'
;

AF23
:
   'af23'
;

AF31
:
   'af31'
;

AF32
:
   'af32'
;

AF33
:
   'af33'
;

AF41
:
   'af41'
;

AF42
:
   'af42'
;

AF43
:
   'af43'
;

AFFINITY
:
   'affinity'
;

AFFINITY_MAP
:
   'affinity-map'
;

AGGREGATE
:
   'aggregate'
;

AGGREGATE_ADDRESS
:
   'aggregate-address'
;

AHP
:
   'ahp'
;

ALERT_GROUP
:
   'alert-group'
;

ALIAS
:
   'alias'
;

ALL
:
   'all'
;

ALLOCATE
:
   'allocate'
;

ALLOW
:
   'allow'
;

ALLOWED
:
   'allowed'
;

ALLOWAS_IN
:
   'allowas-in'
;

ALTERNATE_ADDRESS
:
   'alternate-address'
;

ALWAYS
:
   'always'
;

ALWAYS_COMPARE_MED
:
   'always-compare-med'
;

ALWAYS_ON_VPN
:
   'always-on-vpn'
;

AND
:
   'and'
;

ANTENNA
:
   'antenna'
;

ANY
:
   'any'
;

ANYCONNECT
:
   'anyconnect'
;

ANYCONNECT_ESSENTIALS
:
   'anyconnect-essentials'
;

AOL
:
   'aol'
;

AP
:
   'ap'
;

API
:
   'api'
;

APPLETALK
:
   'appletalk'
;

APPLICATION
:
   'application'
;

APPLY
:
   'apply'
;

AQM_REGISTER_FNF
:
   'aqm-register-fnf'
;

ARAP
:
   'arap'
;

ARCHIVE
:
   'archive'
;

ARCHIVE_LENGTH
:
   'archive-length'
;

ARCHIVE_SIZE
:
   'archive-size'
;

AREA
:
   'area'
;

ARP
:
   'arp'
   { enableIPV6_ADDRESS = false; }

;

AS_OVERRIDE
:
   'as-override'
;

AS_PATH
:
   'as-path' -> pushMode ( M_AsPath )
;

AS_PATH_SET
:
   'as-path-set'
;

AS_SET
:
   'as-set'
;

AS
:
   'as'
;

ASA
:
   'ASA'
;

ASCII_AUTHENTICATION
:
   'ascii-authentication'
;

ASDM
:
   'asdm'
;

ASN
:
   'asn'
;

ASSEMBLER
:
   'assembler'
;

ASSOC_RETRANSMIT
:
   'assoc-retransmit'
;

ASSOCIATE
:
   'associate'
;

ASSOCIATION
:
   'association'
;

ASYNC
:
   'async'
;

ASYNC_BOOTP
:
   'async-bootp'
;

ATM
:
   'atm'
;

ATTEMPTS
:
   'attempts'
;

ATTRIBUTE_DOWNLOAD
:
   'attribute-download'
;

ATTRIBUTE_MAP
:
   'attribute-map'
;

ATTRIBUTE_NAMES
:
   'attribute-names'
;

ATTRIBUTE_SET
:
   'attribute-set'
;

AUDIT
:
   'audit'
;

AUTH_PROXY
:
   'auth-proxy'
;

AUTHENTICATE
:
   'authenticate'
;

AUTHENTICATION
:
   'authentication' -> pushMode ( M_Authentication )
;

AUTHENTICATION_KEY
:
   'authentication-key'
;

AUTHENTICATION_PORT
:
   'authentication-port'
;

AUTHENTICATION_SERVER_GROUP
:
   'authentication-server-group'
;

AUTHORIZATION
:
   'authorization'
;

AUTHORIZATION_REQUIRED
:
   'authorization-required'
;

AUTHORIZATION_SERVER_GROUP
:
   'authorization-server-group'
;

AUTO
:
   'auto'
;

AUTO_COST
:
   'auto-cost'
;

AUTO_LOCAL_ADDR
:
   'auto-local-addr'
;

AUTO_RECOVERY
:
   'auto-recovery'
;

AUTO_RP
:
   'auto-rp'
;

AUTO_SUMMARY
:
   'auto-summary'
;

AUTO_SYNC
:
   'auto-sync'
;

AUTO_TUNNEL
:
   'auto-tunnel'
;

AUTOROUTE
:
   'autoroute'
;

AUTORP
:
   'autorp'
;

AUTOSELECT
:
   'autoselect'
;

AUTOSTATE
:
   'autostate'
;

BACK_UP
:
   'back-up'
;

BACKGROUND_ROUTES_ENABLE
:
   'background-routes-enable'
;

BACKUPCRF
:
   'backupcrf'
;

BANDWIDTH
:
   'bandwidth'
;

BANDWIDTH_PERCENTAGE
:
   'bandwidth-percentage'
;

BANNER
:
   'banner' -> pushMode ( M_Description )
;

BANNER_COMPLEX
:
   'banner' ' '+
   (
      'exec'
      | 'login'
      | 'motd'
   ) -> type ( BANNER ) , pushMode ( M_BANNER )
;

BASH
:
   'bash'
;

BEACON
:
   'beacon'
;

BESTPATH
:
   'bestpath'
;

BEYOND_SCOPE
:
   'beyond-scope'
;

BFD
:
   'bfd'
;

BFD_ENABLE
:
   'bfd-enable'
;

BGP
:
   'bgp'
;

BGP_COMMUNITY
:
   'bgp-community'
;

BIDIR_ENABLE
:
   'bidir-enable'
;

BIDIR_OFFER_INTERVAL
:
   'bidir-offer-interval'
;

BIDIR_OFFER_LIMIT
:
   'bidir-offer-limit'
;

BIFF
:
   'biff'
;

BIND
:
   'bind'
;

BOOT
:
   'boot'
;

BOOT_END_MARKER
:
   'boot-end-marker'
;

BOOT_START_MARKER
:
   'boot-start-marker'
;

BOOTP_RELAY
:
   'bootp-relay'
;

BOOTP
:
   'bootp'
;

BOOTPC
:
   'bootpc'
;

BOOTPS
:
   'bootps'
;

BOTH
:
   'both'
;

BOUNDARY
:
   'boundary'
;

BREAKOUT
:
   'breakout'
;

BRIDGE
:
   'bridge'
;

BRIDGE_GROUP
:
   'bridge-group'
;

BRIDGE_PRIORITY
:
   'bridge-priority'
;

BROADCAST
:
   'broadcast'
;

BROADCAST_ADDRESS
:
   'broadcast-address'
;

BSD_CLIENT
:
   'bsd-client'
;

BSD_USERNAME
:
   'bsd-username'
;

BSR_BORDER
:
   'bsr-border'
;

BSR_CANDIDATE
:
   'bsr-candidate'
;

BUNDLE
:
   'bundle'
;

CA
:
   'ca'
;

CABLE_RANGE
:
   'cable-range'
;

CABLELENGTH
:
   'cablelength' -> pushMode ( M_COMMENT )
;

CACHE
:
   'cache'
;

CACHE_TIMEOUT
:
   'cache-timeout'
;

CALL
:
   'call'
;

CALL_HOME
:
   'call-home'
;

CALLER_ID
:
   'caller-id'
;

CALLHOME
:
   'callhome'
;

CAM_ACL
:
   'cam-acl'
;

CAM_PROFILE
:
   'cam-profile'
;

CARD
:
   'card'
;

CARRIER_DELAY
:
   'carrier-delay'
;

CAS_CUSTOM
:
   'cas-custom'
;

CCM
:
   'ccm'
;

CCM_GROUP
:
   'ccm-group'
;

CCM_MANAGER
:
   'ccm-manager'
;

CDP
:
   'cdp'
;

CDP_URL
:
   'cdp-url'
;

CEF
:
   'cef'
;

CERTIFICATE
:
   'certificate' -> pushMode ( M_Certificate )
;

CFS
:
   'cfs'
;

CGMP
:
   'cgmp'
;

CHANGES
:
   'changes'
;

CHANNEL
:
   'channel'
;

CHANNEL_GROUP
:
   'channel-group'
;

CHANNEL_PROTOCOL
:
   'channel-protocol'
;

CHANNELIZED
:
   'channelized'
;

CHAP
:
   'chap'
;

CHARGEN
:
   'chargen'
;

CHAT_SCRIPT
:
   'chat-script'
;

CIPC
:
   'cipc'
;

CIRCUIT_TYPE
:
   'circuit-type'
;

CISP
:
   'cisp'
;

CITRIX_ICA
:
   'citrix-ica'
;

CLASS
:
   'class'
;

CLASSLESS
:
   'classless'
;

CLASS_MAP
:
   'class-map'
;

CLEANUP
:
   'cleanup'
;

CLI
:
   'cli'
;

CLIENT
:
   'client'
;

CLIENT_GROUP
:
   'client-group'
;

CLNS
:
   'clns'
;

CLOCK
:
   'clock'
;

CLOCK_PERIOD
:
   'clock-period'
;

CLUSTER
:
   'cluster'
;

CLUSTER_ID
:
   'cluster-id'
;

CMD
:
   'cmd'
;

CNS
:
   'cns'
;

CODEC
:
   'codec'
;

COLLECT
:
   'collect'
;

COMM_LIST
:
   'comm-list'
;

COMMAND
:
   'command'
;

COMMANDER_ADDRESS
:
   'commander-address'
   { enableIPV6_ADDRESS = false; }

;

COMMANDS
:
   'commands'
;

COMMIT
:
   'commit'
;

COMMON
:
   'common'
;

COMMUNITY
:
   'community'
   { enableIPV6_ADDRESS = false; }

;

COMMUNITY_LIST
:
   'community-list'
   {
      enableIPV6_ADDRESS = false;
      enableCOMMUNITY_LIST_NUM = true;
      enableDEC = false;
   }

;

COMMUNITY_SET
:
   'community-set'
   {
      inCommunitySet = true;
      enableIPV6_ADDRESS = false;
   }

;

CONFDCONFIG
:
   'confdConfig'
;

CONFIG_COMMANDS
:
   'config-commands'
;

CONFIG_REGISTER
:
   'config-register'
;

CONFIGURATION
:
   'configuration'
;

CONFIGURE
:
   'configure'
;

CONFLICT_POLICY
:
   'conflict-policy'
;

CONFORM_ACTION
:
   'conform-action'
;

CONGESTION_CONTROL
:
   'congestion-control'
;

CONNECT_SOURCE
:
   'connect-source'
;

CONNECTED
:
   'connected'
;

CONNECTION
:
   'connection'
;

CONSOLE
:
   'console'
;

CONTACT_EMAIL_ADDR
:
   'contact-email-addr'
;

CONTACT_NAME
:
   'contact-name' -> pushMode ( M_Description )
;

CONTEXT
:
   'context'
;

CONTRACT_ID
:
   'contract-id'
;

CONTROL_APPS_USE_MGMT_PORT
:
   'control-apps-use-mgmt-port'
;

CONTROL_PLANE
:
   'control-plane'
;

CONTROLLER
:
   'controller'
;

CONVERSION_ERROR
:
   'conversion-error'
;

COPP
:
   'copp'
;

COPY
:
   'copy'
;

COS
:
   'cos'
;

COS_QUEUE_GROUP
:
   'cos-queue-group'
;

COST
:
   'cost'
;

COUNT
:
   'count'
;

COUNTER
:
   'counter'
;

COUNTERS
:
   'counters'
;

CPD
:
   'cpd'
;

CPTONE
:
   'cptone'
;

CRC
:
   'crc'
;

CREDENTIALS
:
   'credentials'
;

CRYPTO
:
   'crypto'
;

CRYPTOGRAPHIC_ALGORITHM
:
   'cryptographic-algorithm'
;

CRL
:
   'crl'
;

CS1
:
   'cs1'
;

CS2
:
   'cs2'
;

CS3
:
   'cs3'
;

CS4
:
   'cs4'
;

CS5
:
   'cs5'
;

CS6
:
   'cs6'
;

CS7
:
   'cs7'
;

CSD
:
   'csd'
;

CSNP_INTERVAL
:
   'csnp-interval'
;

CTIQBE
:
   'ctiqbe'
;

CTL_FILE
:
   'ctl-file'
;

CTS
:
   'cts'
;

CUSTOMER_ID
:
   'customer-id'
;

CWR
:
   'cwr'
;

DAEMON
:
   'daemon'
;

DAMPENING
:
   'dampening'
;

DATABITS
:
   'databits'
;

DAYTIME
:
   'daytime'
;

DBL
:
   'dbl'
;

DCB
:
   'dcb'
;

DCB_BUFFER_THRESHOLD
:
   'dcb-buffer-threshold'
;

DCB_POLICY
:
   'dcb-policy'
;

DCBX
:
   'dcbx'
;

DEAD_INTERVAL
:
   'dead-interval'
;

DEADTIME
:
   'deadtime'
;

DEBUG
:
   'debug'
;

DECAP_GROUP
:
   'decap-group'
;

DEFAULT
:
   'default'
;

DEFAULT_ACTION
:
   'default-action'
;

DEFAULT_DOMAIN
:
   'default-domain'
;

DEFAULT_GATEWAY
:
   'default-gateway'
;

DEFAULT_GROUP_POLICY
:
   'default-group-policy'
;

DEFAULT_GW
:
   'default-gw'
;

DEFAULT_INFORMATION
:
   'default-information'
;

DEFAULT_INFORMATION_ORIGINATE
:
   'default-information-originate'
;

DEFAULT_INSPECTION_TRAFFIC
:
   'default-inspection-traffic'
;

DEFAULT_MAX_FRAME_SIZE
:
   'default-max-frame-size'
;

DEFAULT_METRIC
:
   'default-metric'
;

DEFAULT_NETWORK
:
   'default-network'
;

DEFAULT_ORIGINATE
:
   'default-originate'
;

DEFAULT_ROLE
:
   'default-role'
;

DEFAULT_ROUTER
:
   'default-router'
;

DEFAULT_VALUE
:
   'default-value'
;

DEFINITION
:
   'definition'
;

DEL
:
   'Del'
;

DELAY
:
   'delay'
;

DELAY_START
:
   'delay-start'
;

DELETE
:
   'delete'
;

DENY
:
   'deny'
;

DES
:
   'des' -> pushMode ( M_DES )
;

DES_SHA1
:
   'des-sha1'
;

DESCRIPTION
:
   'description' -> pushMode ( M_Description )
;

DESIRABLE
:
   'desirable'
;

DESTINATION
:
   'destination'
;

DESTINATION_PATTERN
:
   'destination-pattern'
;

DESTINATION_PROFILE
:
   'destination-profile'
;

DESTINATION_VRF
:
   'destination-vrf'
;

DETAIL
:
   'detail'
;

DETERMINISTIC_MED
:
   'deterministic-med'
;

DEVICE
:
   'device'
;

DEVICE_SENSOR
:
   'device-sensor'
;

DISABLE_PEER_AS_CHECK
:
   'disable-peer-as-check'
;

DF
:
   'df'
;

DFA_REGEX
:
   'dfa-regex'
;

DHCP
:
   'dhcp'
;

DHCPD
:
   'dhcpd'
;

DHCPRELAY
:
   'dhcprelay'
;

DIAGNOSTIC
:
   'diagnostic'
;

DIAL_PEER
:
   'dial-peer'
;

DIALER_LIST
:
   'dialer-list'
;

DIRECT
:
   'direct'
;

DIRECTED_BROADCAST
:
   'directed-broadcast'
;

DISABLE
:
   'disable'
;

DISABLE_ADVERTISEMENT
:
   'disable-advertisement'
;

DISCARD
:
   'discard'
;

DISCOVERY
:
   'discovery'
;

DISTANCE
:
   'distance'
;

DISTRIBUTE
:
   'distribute'
;

DISTRIBUTE_LIST
:
   'distribute-list'
;

DISTRIBUTION
:
   'distribution'
;

DM_FALLBACK
:
   'dm-fallback'
;

DNS
:
   'dns'
;

DNS_GUARD
:
   'dns-guard'
;

DNS_SERVER
:
   'dns-server'
;

DNSIX
:
   'dnsix'
;

DO
:
   'do'
;

DOMAIN
:
   'domain'
;

DOMAIN_ID
:
   'domain-id'
;

DOMAIN_LIST
:
   'domain-list'
;

DOMAIN_LOOKUP
:
   'domain-lookup'
;

DOMAIN_NAME
:
   'domain-name'
;

DONE
:
   'done'
;

DONT_CAPABILITY_NEGOTIATE
:
   'dont-capability-negotiate'
;

DOT11
:
   'dot11'
;

DOT1Q
:
   'dot1q'
;

DOT1Q_TUNNEL
:
   'dot1q-tunnel'
;

DOT1X
:
   'dot1x'
;

DROP
:
   'drop'
;

DS0_GROUP
:
   'ds0-group'
;

DSCP
:
   'dscp'
;

DSP
:
   'dsp'
;

DSPFARM
:
   'dspfarm'
;

DSS
:
   'dss'
;

DSU
:
   'dsu'
;

DTMF_RELAY
:
   'dtmf-relay'
;

DUAL_ACTIVE
:
   'dual-active'
;

DUPLEX
:
   'duplex'
;

DVMRP
:
   'dvmrp'
;

DYNAMIC
:
   'dynamic'
;

DYNAMIC_ACCESS_POLICY_RECORD
:
   'dynamic-access-policy-record'
;

DYNAMIC_MAP
:
   'dynamic-map'
;

EBGP
:
   'ebgp'
;

EBGP_MULTIHOP
:
   'ebgp-multihop'
;

ECE
:
   'ece'
;

ECHO
:
   'echo'
;

ECHO_CANCEL
:
   'echo-cancel'
;

ECHO_REPLY
:
   'echo-reply'
;

ECHO_REQUEST
:
   'echo-request'
;

ECMP
:
   'ecmp'
;

ECMP_GROUP
:
   'ecmp-group'
;

ECN
:
   'ecn'
;

EF
:
   'ef'
;

EGP
:
   'egp'
;

EGRESS
:
   'egress'
;

EGRESS_INTERFACE_SELECTION
:
   'egress-interface-selection'
;

EIGRP
:
   'eigrp'
;

ELSE
:
   'else'
;

ELSEIF
:
   'elseif'
;

EMAIL
:
   'email'
;

EMAIL_ADDR
:
   'email-addr' -> pushMode ( M_Description )
;

EMAIL_CONTACT
:
   'email-contact' -> pushMode ( M_Description )
;

EMPTY
:
   'empty'
;

ENABLE
:
   'enable'
;

ENCAPSULATION
:
   'encapsulation'
;

ENCR
:
   'encr'
;

ENCRYPTION
:
   'encryption'
;

END
:
   'end'
;

ENDIF
:
   'endif'
;

END_CLASS_MAP
:
   'end-class-map'
;

END_POLICY
:
   'end-policy'
;

END_POLICY_MAP
:
   'end-policy-map'
;

END_SET
:
   'end-set'
   { inCommunitySet = false; }

;

ENFORCE_FIRST_AS
:
   'enforce-first-as'
;

ENGINEID
:
   'engineid' -> pushMode ( M_COMMENT )
;

ENROLLMENT
:
   'enrollment'
;

ENVIRONMENT
:
   'environment'
;

EOF_LITERAL
:
   'EOF'
;

EOU
:
   'eou'
;

EQ
:
   'eq'
;

ERRDISABLE
:
   'errdisable'
;

ERROR
:
   'error'
;

ERROR_ENABLE
:
   'error-enable'
;

ERROR_RECOVERY
:
   'error-recovery'
;

ERSPAN_ID
:
   'erspan-id'
;

ESCAPE_CHARACTER
:
   'escape-character'
;

ESP
:
   'esp'
;

ESTABLISHED
:
   'established'
;

ETHERNET
:
   'ethernet'
;

EVALUATE
:
   'evaluate'
;

EVENT
:
   'event'
;

EVENT_HANDLER
:
   'event-handler'
;

EVENT_HISTORY
:
   'event-history'
;

EXACT
:
   'exact'
;

EXCEED_ACTION
:
   'exceed-action'
;

EXCEPTION
:
   'exception'
;

EXCEPTION_SLAVE
:
   'exception-slave'
;

EXCLUDE
:
   'exclude'
;

EXEC
:
   'exec'
;

EXEC_TIMEOUT
:
   'exec-timeout'
;

EXECUTE
:
   'execute' -> pushMode ( M_Execute )
;

EXIT
:
   'exit'
;

EXIT_ADDRESS_FAMILY
:
   'exit-address-family'
;

EXIT_PEER_POLICY
:
   'exit-peer-policy'
;

EXIT_PEER_SESSION
:
   'exit-peer-session'
;

EXIT_VRF
:
   'exit-vrf'
;

EXPECT
:
   'expect'
;

EXPORT
:
   'export'
;

EXPORT_PROTOCOL
:
   'export-protocol'
;

EXPORTER
:
   'exporter'
;

EXPANDED
:
   'expanded'
;

EXTCOMM_LIST
:
   'extcomm-list'
;

EXTCOMMUNITY
:
   'extcommunity' -> pushMode ( M_Extcommunity )
;

EXTCOMMUNITY_LIST
:
   'extcommunity-list'
;

EXTENDED
:
   'extended'
   { enableDEC = true; enableACL_NUM = false; }

;

EXTENDED_COUNTERS
:
   'extended-counters'
;

EXTENDED_DELAY
:
   'extended-delay'
;

EXTERNAL
:
   'external'
;

FABRIC
:
   'fabric'
;

FACILITY_ALARM
:
   'facility-alarm'
;

FAIL_MESSAGE
:
   'fail-message'
;

FAILED
:
   'failed'
;

FAILOVER
:
   'failover'
;

FAILURE
:
   'failure'
;

FAIR_QUEUE
:
   'fair-queue'
;

FALL_OVER
:
   'fall-over'
;

FALLBACK
:
   'fallback'
;

FALLBACK_DN
:
   'fallback-dn'
;

FAST_FLOOD
:
   'fast-flood'
;

FAST_REROUTE
:
   'fast-reroute'
;

FEATURE
:
   'feature'
;

FEATURE_SET
:
   'feature-set'
;

FEX
:
   'fex'
;

FEX_FABRIC
:
   'fex-fabric'
;

FIELDS
:
   'fields'
;

FILE
:
   'file'
;

FILE_BROWSING
:
   'file-browsing'
;

FILE_ENTRY
:
   'file-entry'
;

FILE_SIZE
:
   'file-size'
;

FILTER_LIST
:
   'filter-list'
;

FIREWALL
:
   'firewall'
   { enableIPV6_ADDRESS = false; }

;

FIN
:
   'fin'
;

FINGER
:
   'finger'
;

FIRMWARE
:
   'firmware'
;

FLOW
:
   'flow'
;

FLOW_AGGREGATION
:
   'flow-aggregation'
;

FLOW_CAPTURE
:
   'flow-capture'
;

FLOW_CACHE
:
   'flow-cache'
;

FLOW_CONTROL
:
   'flow-control'
;

FLOW_EXPORT
:
   'flow-export'
;

FLOW_SAMPLING_MODE
:
   'flow-sampling-mode'
;

FLOW_TOP_TALKERS
:
   'flow-top-talkers'
;

FLOWCONTROL
:
   'flowcontrol'
;

FLUSH_AT_ACTIVATION
:
   'flush-at-activation'
;

FORCE
:
   'force'
;

FORMAT
:
   'format'
;

FORWARD_DIGITS
:
   'forward-digits'
;

FORWARD_PROTOCOL
:
   'forward-protocol'
;

FORWARDER
:
   'forwarder'
;

FORWARDING
:
   'forwarding'
;

FPD
:
   'fpd'
;

FQDN
:
   'fqdn'
;

FRAGMENTS
:
   'fragments'
;

FRAME_RELAY
:
   'frame-relay'
;

FRAMING
:
   'framing'
;

FREQUENCY
:
   'frequency'
;

FROM
:
   'from'
;

FT
:
   'ft'
;

FTP
:
   'ftp'
;

FTP_DATA
:
   'ftp-data'
;

FTP_SERVER
:
   'ftp-server'
;

FULL_DUPLEX
:
   'full-duplex'
;

FULL_TXT
:
   'full-txt'
;

GATEKEEPER
:
   'gatekeeper'
;

GATEWAY
:
   'gateway'
;

GE
:
   'ge'
;

GID
:
   'gid'
;

GIG_DEFAULT
:
   'gig-default'
;

GLBP
:
   'glbp'
;

GLOBAL_MTU
:
   'global-mtu'
;

GOPHER
:
   'gopher'
;

GRACEFUL_RESTART
:
   'graceful-restart'
;

GRATUITOUS_ARPS
:
   'gratuitous-arps'
;

GRE
:
   'gre'
;

GREEN
:
   'green'
;

GROUP
:
   'group'
;

GROUP_ALIAS
:
   'group-alias'
;

GROUP_LIST
:
   'group-list'
;

GROUP_LOCK
:
   'group-lock'
;

GROUP_OBJECT
:
   'group-object'
;

GROUP_POLICY
:
   'group-policy'
;

GROUP_RANGE
:
   'group-range'
;

GROUP_URL
:
   'group-url'
;

GSHUT
:
   'GSHUT'
;

GT
:
   'gt'
;

H323
:
   'h323'
;

H323_GATEWAY
:
   'h323-gateway'
;

HALF_DUPLEX
:
   'half-duplex'
;

HARDWARE
:
   'hardware'
;

HASH
:
   'hash'
;

HASH_ALGORITHM
:
   'hash-algorithm'
;

HEARTBEAT_INTERVAL
:
   'heartbeat-interval'
;

HEARTBEAT_TIME
:
   'heartbeat-time'
;

HELLO
:
   'hello'
;

HELLO_INTERVAL
:
   'hello-interval'
;

HELLO_MULTIPLIER
:
   'hello-multiplier'
;

HELLO_PADDING
:
   'hello-padding'
;

HELLO_PASSWORD
:
   'hello-password'
;

HELPER_ADDRESS
:
   'helper-address'
;

HIDDEN_LITERAL
:
   'hidden'
;

HIDDEN_SHARES
:
   'hidden-shares'
;

HIDEKEYS
:
   'hidekeys'
;

HIGH_AVAILABILITY
:
   'high-availability'
;

HISTORY
:
   'history'
;

HOLD_QUEUE
:
   'hold-queue'
;

HOMEDIR
:
   'homedir'
;

HOP_LIMIT
:
   'hop-limit'
;

HOST
:
   'host'
;

HOST_ASSOCIATION
:
   'host-association'
;

HOST_INFO
:
   'host-info'
;

HOST_ROUTING
:
   'host-routing'
;

HOST_UNKNOWN
:
   'host-unknown'
;

HOST_UNREACHABLE
:
   'host-unreachable'
;

HOSTNAME
:
   'hostname'
;

HPM
:
   'hpm'
;

HSRP
:
   'hsrp'
;

HTTP
:
   'http'
;

HTTP_COMMANDS
:
   'http-commands'
;

HTTPS
:
   'https'
;

HW_MODULE
:
   'hw-module'
;

HW_SWITCH
:
   'hw-switch'
;

ICMP
:
   'icmp'
;

ICMP_ECHO
:
   'icmp-echo'
;

ICMP_ERRORS
:
   'icmp-errors'
;

ICMP_OBJECT
:
   'icmp-object'
;

ICMP_TYPE
:
   'icmp-type'
;

ICMP6
:
   'icmp6'
;

ICMPV6
:
   'icmpv6'
;

ID_MISMATCH
:
   'id-mismatch'
;

ID_RANDOMIZATION
:
   'id-randomization'
;

IDENT
:
   'ident'
;

IDENTITY
:
   'identity'
;

IDLE
:
   'idle'
;

IDLE_TIMEOUT
:
   'idle-timeout'
;

IF
:
   'if'
;

IF_NEEDED
:
   'if-needed'
;

IGMP
:
   'igmp'
;

IGP_COST
:
   'igp-cost'
;

IGRP
:
   'igrp'
;

IGNORE
:
   'ignore'
;

IGP
:
   'igp'
;

IKEV1
:
   'ikev1'
;

ILMI_KEEPALIVE
:
   'ilmi-keepalive'
;

IMAP4
:
   'imap4'
;

IMPORT
:
   'import'
;

IN
:
   'in'
;

INACTIVITY_TIMER
:
   'inactivity-timer'
;

INBAND
:
   'inband'
;

INBOUND
:
   'inbound'
;

INCLUDE
:
   'include'
;

INCOMING
:
   'incoming'
;

INCOMPLETE
:
   'incomplete'
;

INFINITY
:
   'infinity'
;

INFORMATION_REPLY
:
   'information-reply'
;

INFORMATION_REQUEST
:
   'information-request'
;

INGRESS
:
   'ingress'
;

INHERIT
:
   'inherit'
;

INPUT
:
   'input'
;

INSERVICE
:
   'inservice'
;

INSPECT
:
   'inspect'
;

INSTALL
:
   'install'
;

INSTANCE
:
   'instance'
;

INTEGRITY
:
   'integrity'
;

INTERFACE
:
   'interface'
   { enableIPV6_ADDRESS = false; }

   -> pushMode ( M_Interface )
;

INTERNAL
:
   'internal'
;

INTERNET
:
   'internet'
;

INTERVAL
:
   'interval'
;

INVALID_SPI_RECOVERY
:
   'invalid-spi-recovery'
;

INVALID_USERNAME_LOG
:
   'invalid-username-log'
;

IOS_REGEX
:
   'ios-regex'
;

IP
:
   'ip'
;

IP_ADDRESS_LITERAL
:
   'ip-address'
;

IPC
:
   'ipc'
;

IPINIP
:
   'ipinip'
;

IPSEC
:
   'ipsec'
;

IPSEC_UDP
:
   'ipsec-udp'
;

IPSLA
:
   'ipsla'
;

IPV4
:
   'ipv4'
;

IPV6
:
   'ipv6'
;

IPV6_ADDRESS_POOL
:
   'ipv6-address-pool'
;

IPX
:
   'ipx'
;

IRC
:
   'irc'
;

IRDP
:
   'irdp'
;

ISAKMP
:
   'isakmp'
;

ISDN
:
   'isdn'
;

IS
:
   'is'
;

IS_TYPE
:
   'is-type'
;

ISIS
:
   'isis'
;

ISL
:
   'isl'
;

ISPF
:
   'ispf'
;

ISSUER_NAME
:
   'issuer-name'
;

KEEPALIVE
:
   'keepalive'
;

KEEPALIVE_ENABLE
:
   'keepalive-enable'
;

KEEPOUT
:
   'keepout'
;

KERBEROS
:
   'kerberos'
;

KEY
:
   'key' -> pushMode ( M_Key )
;

KEY_SOURCE
:
   'key-source'
;

KEY_STRING
:
   'key-string'
;

KEYPAIR
:
   'keypair'
;

KEYPATH
:
   'keypath'
;

KEYRING
:
   'keyring'
;

KLOGIN
:
   'klogin'
;

KRON
:
   'kron'
;

KSHELL
:
   'kshell'
;

L2_FILTER
:
   'l2-filter'
;

L2TP
:
   'l2tp'
;

L2TP_CLASS
:
   'l2tp-class'
;

L2TRANSPORT
:
   'l2transport'
;

L2VPN
:
   'l2vpn'
;

LABEL
:
   'label'
;

LACP
:
   'lacp'
;

LAN
:
   'lan'
;

LANE
:
   'lane'
;

LAPB
:
   'lapb'
;

LAST_AS
:
   'last-as'
;

LDAP
:
   'ldap'
;

LDAPS
:
   'ldaps'
;

LDP
:
   'ldp'
;

LE
:
   'le'
;

LEASE
:
   'lease'
;

LEVEL_1
:
   'level-1'
;

LEVEL_1_2
:
   'level-1-2'
;

LEVEL_2
:
   'level-2'
;

LEVEL_2_ONLY
:
   'level-2-only'
;

LDAP_BASE_DN
:
   'ldap-base-dn'
;

LDAP_LOGIN
:
   'ldap-login'
;

LDAP_LOGIN_DN
:
   'ldap-login-dn'
;

LDAP_NAMING_ATTRIBUTE
:
   'ldap-naming-attribute'
;

LDAP_SCOPE
:
   'ldap-scope'
;

LENGTH
:
   'length'
;

LICENSE
:
   'license'
;

LIFE
:
   'life'
;

LIFETIME
:
   'lifetime'
;

LIMIT
:
   'limit'
;

LIMIT_RESOURCE
:
   'limit-resource'
;

LINE
:
   'line'
;

LINECARD
:
   'linecard'
;

LINECODE
:
   'linecode'
;

LINK
:
   'link'
;

LINK_FAULT_SIGNALING
:
   'link-fault-signaling'
;

LIST
:
   'list'
;

LISTEN
:
   'listen'
;

LLDP
:
   'lldp'
;

LOAD_BALANCE
:
   'load-balance'
;

LOAD_BALANCING
:
   'load-balancing'
;

LOAD_INTERVAL
:
   'load-interval'
;

LOAD_SHARING
:
   'load-sharing'
;

LOCAL
:
   'local'
;

LOCAL_AS
:
   'local-as'
;

LOCAL_ASA
:
   'LOCAL'
;

LOCAL_INTERFACE
:
   'local-interface'
;

LOCAL_IP
:
   'local-ip'
;

LOCAL_PORT
:
   'local-port'
;

LOCAL_PREFERENCE
:
   'local-preference'
;

LOCAL_V6_ADDR
:
   'local-v6-addr'
;

LOCATION
:
   'location' -> pushMode ( M_COMMENT )
;

LOG
:
   'log'
;

LOG_ADJACENCY_CHANGES
:
   'log-adjacency-changes'
;

LOG_INPUT
:
   'log-input'
;

LOG_NEIGHBOR_CHANGES
:
   'log-neighbor-changes'
;

LOGGING
:
   'logging'
;

LOGIN
:
   'login'
;

LOTUSNOTES
:
   'lotusnotes'
;

LPD
:
   'lpd'
;

LPTS
:
   'lpts'
;

LRE
:
   'lre'
;

LSP_GEN_INTERVAL
:
   'lsp-gen-interval'
;

LSP_INTERVAL
:
   'lsp-interval'
;

LSP_PASSWORD
:
   'lsp-password'
;

LSP_REFRESH_INTERVAL
:
   'lsp-refresh-interval'
;

LT
:
   'lt'
;

MAC
:
   'mac'
;

MAC_ADDRESS
:
   'mac-address' -> pushMode ( M_COMMENT )
;

MAC_ADDRESS_TABLE
:
   'mac-address-table'
;

MAC_LEARN
:
   'mac-learn'
;

MACRO
:
   'macro'
;

MAIL_SERVER
:
   'mail-server'
;

MAIN_CPU
:
   'main-cpu'
;

MANAGEMENT
:
   'management'
;

MANAGEMENT_ACCESS
:
   'management-access'
;

MANAGEMENT_ONLY
:
   'management-only'
;

MANAGEMENT_PLANE
:
   'management-plane'
;

MAP
:
   'map'
;

MAP_CLASS
:
   'map-class'
;

MAP_GROUP
:
   'map-group'
;

MAP_LIST
:
   'map-list'
;

MAPPING
:
   'mapping'
;

MASK
:
   'mask'
;

MASK_REPLY
:
   'mask-reply'
;

MASK_REQUEST
:
   'mask-request'
;

MASTER
:
   'master'
;

MATCH
:
   'match'
;

MATCH_ALL
:
   'match-all'
;

MATCH_ANY
:
   'match-any'
;

MATCHES_ANY
:
   'matches-any'
;

MATCHES_EVERY
:
   'matches-every'
;

MAX_ASSOCIATIONS
:
   'max-associations'
;

MAX_LSA
:
   'max-lsa'
;

MAX_LSP_LIFETIME
:
   'max-lsp-lifetime'
;

MAX_METRIC
:
   'max-metric'
;

MAX_ROUTE
:
   'max-route'
;

MAXIMUM
:
   'maximum'
;

MAXIMUM_ACCEPTED_ROUTES
:
   'maximum-accepted-routes'
;

MAXIMUM_PATHS
:
   'maximum-paths'
;

MAXIMUM_PEERS
:
   'maximum-peers'
;

MAXIMUM_PREFIX
:
   'maximum-prefix'
;

MAXIMUM_ROUTES
:
   'maximum-routes'
;

MCAST_BOUNDARY
:
   'mcast-boundary'
;

MDIX
:
   'mdix'
;

MDT
:
   'mdt'
;

MED
:
   'med'
;

MEDIA_TERMINATION
:
   'media-termination'
;

MEDIA_TYPE
:
   'media-type'
;

MEMBER
:
   'member'
;

MEMORY_SIZE
:
   'memory-size'
;

MESH_GROUP
:
   'mesh-group'
;

MESSAGE_DIGEST
:
   'message-digest'
;

MESSAGE_DIGEST_KEY
:
   'message-digest-key'
;

MESSAGE_LENGTH
:
   'message-length'
;

MESSAGE_LEVEL
:
   'message-level'
;

METRIC
:
   'metric'
;

METRIC_STYLE
:
   'metric-style'
;

METRIC_TYPE
:
   'metric-type'
;

MFIB
:
   'mfib'
;

MFIB_MODE
:
   'mfib-mode'
;

MGCP
:
   'mgcp'
;

MICROCODE
:
   'microcode'
;

MINIMAL
:
   'minimal'
;

MINIMUM_LINKS
:
   'minimum-links'
;

MLAG
:
   'mlag'
;

MLD
:
   'mld'
;

MLD_QUERY
:
   'mld-query'
;

MLD_REDUCTION
:
   'mld-reduction'
;

MLD_REPORT
:
   'mld-report'
;

MLS
:
   'mls'
;

MOBILE_IP
:
   'mobile-ip'
;

MOBILE_REDIRECT
:
   'mobile-redirect'
;

MOBILITY
:
   'mobility'
;

MODE
:
   'mode'
;

MODEM
:
   'modem'
;

MODULE
:
   'module'
;

MONITOR
:
   'monitor'
;

MONITOR_INTERFACE
:
   'monitor-interface'
;

MONITORING
:
   'monitoring'
;

MOP
:
   'mop'
;

MOTD
:
   'motd'
;

MPLS
:
   'mpls'
;

MPLS_LABEL
:
   'mpls-label'
;

MROUTE
:
   'mroute'
;

MROUTE_CACHE
:
   'mroute-cache'
;

MSDP
:
   'msdp'
;

MSCHAP
:
   'mschap'
;

MSCHAPV2
:
   'mschapv2'
;

MSIE_PROXY
:
   'msie-proxy'
;

MSRPC
:
   'msrpc'
;

MTA
:
   'mta'
;

MTU
:
   'mtu'
;

MTU_IGNORE
:
   'mtu-ignore'
;

MULTICAST
:
   'multicast'
;

MULTICAST_BOUNDARY
:
   'multicast-boundary'
;

MULTICAST_ROUTING
:
   'multicast-routing'
;

MULTILINK
:
   'multilink'
;

MULTIPATH
:
   'multipath'
;

MULTIPATH_RELAX
:
   'multipath-relax'
;

MULTIPOINT
:
   'multipoint'
;

MULTI_CONFIG
:
   'multi-config'
;

MULTI_TOPOLOGY
:
   'multi-topology'
;

MVR
:
   'mvr'
;

NAME
:
   'name' -> pushMode ( M_Name )
;

NAME_LOOKUP
:
   'name-lookup'
;

NAME_SERVER
:
   'name-server'
;

NAMEIF
:
   'nameif'
;

NAMESPACE
:
   'namespace'
;

NAMES
:
   'names'
;

NAMESERVER
:
   'nameserver'
;

NAT
:
   'nat'
;

NAT_CONTROL
:
   'nat-control'
;

NATIVE
:
   'native'
;

NATPOOL
:
   'natpool'
;

ND
:
   'nd'
;

ND_NA
:
   'nd-na'
;

ND_NS
:
   'nd-ns'
;

NEGOTIATE
:
   'negotiate'
;

NEGOTIATION
:
   'negotiation'
;

NEIGHBOR
:
   'neighbor' -> pushMode ( M_NEIGHBOR )
;

NEIGHBOR_DOWN
:
   'neighbor-down'
;

NEIGHBOR_GROUP
:
   'neighbor-group'
;

NEQ
:
   'neq'
;

NESTED
:
   'nested'
;

NET
:
   'net' -> pushMode ( M_ISO_Address )
;

NET_UNREACHABLE
:
   'net-unreachable'
;

NETBIOS_DGM
:
   'netbios-dgm'
;

NETBIOS_NS
:
   'netbios-ns'
;

NETBIOS_SS
:
   'netbios-ss'
;

NETBIOS_SSN
:
   'netbios-ssn'
;

NETCONF
:
   'netconf'
;

NETWORK
:
   'network'
;

NETWORK_CLOCK_PARTICIPATE
:
   'network-clock-participate'
;

NETWORK_CLOCK_SELECT
:
   'network-clock-select'
;

NETWORK_OBJECT
:
   'network-object'
;

NETWORK_UNKNOWN
:
   'network-unknown'
;

NEW_MODEL
:
   'new-model'
;

NEWINFO
:
   'newinfo'
;

NEXT_HOP
:
   'next-hop'
;

NEXT_HOP_SELF
:
   'next-hop-self'
;

NEXTHOP
:
   'nexthop'
;

NEXTHOP1
:
   'nexthop1'
;

NEXTHOP2
:
   'nexthop2'
;

NEXTHOP_ATTRIBUTE
:
   'nexthop-attribute'
;

NHOP_ONLY
:
   'nhop-only'
;

NLRI
:
   'nlri'
;

NLS
:
   'nls'
;

NMSP
:
   'nmsp'
;

NNTP
:
   'nntp'
;

NO
:
   'no'
;

NO_ADVERTISE
:
   'no-advertise'
;

NO_BANNER
:
   'no' F_Whitespace+ 'banner'
;

NO_EXPORT
:
   'no-export'
;

NO_PREPEND
:
   'no-prepend'
;

NO_REDISTRIBUTION
:
   'no-redistribution'
;

NO_SUMMARY
:
   'no-summary'
;

NODE
:
   'node'
;

NON500_ISAKMP
:
   'non500-isakmp'
;

NON_BROADCAST
:
   'non-broadcast'
;

NON_CLIENT_NRT
:
   'non-client-nrt'
;

NON_DETERMINISTIC_MED
:
   'non-deterministic-med'
;

NONE
:
   'none'
;

NONEGOTIATE
:
   'nonegotiate'
;

NOS
:
   'nos'
;

NOT
:
   'not'
;

NOT_ADVERTISE
:
   'not-advertise'
;

NOTIFICATION_TIMER
:
   'notification-timer'
;

NOTIFY
:
   'notify'
;

NSF
:
   'nsf'
;

NSR
:
   'nsr'
;

NSSA
:
   'nssa'
;

NSSA_EXTERNAL
:
   'nssa-external'
;

NTP
:
   'ntp'
;

OAM
:
   'oam'
;

OBJECT
:
   'object'
;

OBJECT_GROUP
:
   'object-group'
;

ONE
:
   'one'
;

ONEP
:
   'onep'
;

OPEN
:
   'open'
;

OPENFLOW
:
   'openflow'
;

OPERATION
:
   'operation'
;

OPS
:
   'ops'
;

OPTIMIZED
:
   'optimized'
;

OPTION
:
   'option'
;

OPTIONS
:
   'options'
;

OR
:
   'or'
;

ORIGIN
:
   'origin'
;

ORIGINATE
:
   'originate'
;

ORIGINATES_FROM
:
   'originates-from'
;

ORIGINATOR_ID
:
   'originator-id'
;

OSPF
:
   'ospf'
;

OSPFV3
:
   'ospfv3'
;

OTHER_ACCESS
:
   'other-access'
;

OUI
:
   'oui' -> pushMode ( M_COMMENT )
;

OUT
:
   'out'
;

OUT_OF_BAND
:
   'out-of-band'
;

OUTPUT
:
   'output'
;

OVERRIDE
:
   'override'
;

OWNER
:
   'owner'
;

P2P
:
   'p2p'
;

PACKET_TOO_BIG
:
   'packet-too-big'
;

PAGER
:
   'pager'
;

PARAMETER_PROBLEM
:
   'parameter-problem'
;

PARAMETERS
:
   'parameters'
;

PARENT
:
   'parent'
;

PARITY
:
   'parity'
;

PARSER
:
   'parser'
;

PARTICIPATE
:
   'participate'
;

PASS
:
   'pass'
;

PASSES_THROUGH
:
   'passes-through'
;

PASSIVE
:
   'passive'
;

PASSIVE_INTERFACE
:
   'passive-interface'
;

PASSIVE_ONLY
:
   'passive-only'
;

PASSWORD
:
   'password' -> pushMode ( M_COMMENT )
;

PASSWORD_POLICY
:
   'password-policy'
;

PASSWORD_PROMPT
:
   'password-prompt'
;

PASSWORD_STORAGE
:
   'password-storage'
;

PATH_JITTER
:
   'path-jitter'
;

PATH_OPTION
:
   'path-option'
;

PATH_RETRANSMIT
:
   'path-retransmit'
;

PAUSE
:
   'pause'
;

PCANYWHERE_DATA
:
   'pcanywhere-data'
;

PCANYWHERE_STATUS
:
   'pcanywhere-status'
;

PEER
:
   'peer'
;

PEER_ADDRESS
:
   'peer-address'
;

PEER_CONFIG_CHECK_BYPASS
:
   'peer-config-check-bypass'
;

PEER_GROUP
:
   'peer-group' -> pushMode ( M_NEIGHBOR )
;

PEER_GATEWAY
:
   'peer-gateway'
;

PEER_ID_VALIDATE
:
   'peer-id-validate'
;

PEER_KEEPALIVE
:
   'peer-keepalive'
;

PEER_LINK
:
   'peer-link'
;

PEER_POLICY
:
   'peer-policy'
;

PEER_SESSION
:
   'peer-session'
;

PEER_SWITCH
:
   'peer-switch'
;

PERIODIC
:
   'periodic'
;

PERMANENT
:
   'permanent'
;

PERMIT
:
   'permit'
;

PERSISTENT
:
   'persistent'
;

PFC
:
   'pfc'
;

PHONE_CONTACT
:
   'phone-contact' -> pushMode ( M_Description )
;

PHONE_NUMBER
:
   'phone-number'
;

PHONE_PROXY
:
   'phone-proxy'
;

PHYSICAL_LAYER
:
   'physical-layer'
;

PICKUP
:
   'pickup'
;

PIM
:
   'pim'
;

PIM_AUTO_RP
:
   'pim-auto-rp'
;

PIM_SPARSE
:
   'pim-sparse'
;

PINNING
:
   'pinning'
;

PKI
:
   'pki'
;

PLAT
:
   'plat'
;

PLATFORM
:
   'platform'
;

POINT_TO_MULTIPOINT
:
   'point-to-multipoint'
;

POINT_TO_POINT
:
   'point-to-point'
;

POLICE
:
   'police'
;

POLICY
:
   'policy'
;

POLICY_LIST
:
   'policy-list'
;

POLICY_MAP
:
   'policy-map'
;

POLICY_MAP_INPUT
:
   'policy-map-input'
;

POLICY_MAP_OUTPUT
:
   'policy-map-output'
;

POP2
:
   'pop2'
;

POP3
:
   'pop3'
;

PORT
:
   'port'
;

PORT_CHANNEL
:
   'port-channel'
;

PORT_CHANNEL_PROTOCOL
:
   'port-channel-protocol'
;

PORT_NAME
:
   'port-name'
;

PORT_OBJECT
:
   'port-object'
;

PORT_PROFILE
:
   'port-profile'
;

PORT_SECURITY
:
   'port-security'
;

PORT_UNREACHABLE
:
   'port-unreachable'
;

PORTMODE
:
   'portmode'
;

POS
:
   'pos'
;

POWER
:
   'power'
;

POWEROFF
:
   'poweroff'
;

PPP
:
   'ppp'
;

PPTP
:
   'pptp'
;

PRC_INTERVAL
:
   'prc-interval'
;

PRE_SHARED_KEY
:
   'pre-shared-key'
;

PRECEDENCE
:
   'precedence'
;

PRECONFIGURE
:
   'preconfigure'
;

PREDICTOR
:
   'predictor'
;

PREEMPT
:
   'preempt'
;

PREFIX
:
   'prefix'
;

PREFIX_LIST
:
   'prefix-list'
;

PREFIX_SET
:
   'prefix-set'
;

PREPEND
:
   'prepend'
;

PRF
:
   'prf'
;

PRI_GROUP
:
   'pri-group'
;

PRIMARY
:
   'primary'
;

PRIMARY_PRIORITY
:
   'primary-priority'
;

PRIORITY
:
   'priority'
;

PRIORITY_FLOW_CONTROL
:
   'priority-flow-control'
;

PRIORITY_QUEUE
:
   'priority-queue'
;

PRIVATE_AS
:
   'private-as'
;

PRIVATE_VLAN
:
   'private-vlan'
;

PRIVILEGE
:
   'privilege'
;

PRIVILEGE_MODE
:
   'privilege-mode'
;

PROBE
:
   'probe'
;

PROCESS
:
   'process'
;

PROFILE
:
   'profile'
;

PROMPT
:
   'prompt'
;

PROPOSAL
:
   'proposal'
;

PROTOCOL
:
   'protocol'
;

PROTOCOL_OBJECT
:
   'protocol-object'
;

PROTOCOL_VIOLATION
:
   'protocol-violation'
;

PROVISION
:
   'provision'
;

PROXY_ARP
:
   'proxy-arp'
;

PROXY_SERVER
:
   'proxy-server'
;

PSEUDOWIRE_CLASS
:
   'pseudowire-class'
;

PSH
:
   'psh'
;

PTP
:
   'ptp'
;

QOS
:
   'qos'
;

QOS_GROUP
:
   'qos-group'
;

QOS_MAPPING
:
   'qos-mapping'
;

QOS_POLICY_OUTPUT
:
   'qos-policy-output'
;

QUERY_ONLY
:
   'query-only'
;

QUEUE
:
   'queue'
;

QUEUE_BUFFERS
:
   'queue-buffers'
;

QUEUE_LIMIT
:
   'queue-limit'
;

QUEUE_SET
:
   'queue-set'
;

QUIT
:
   'quit'
;

RADIUS
:
   'radius'
;

RADIUS_ACCT
:
   'radius-acct'
;

RADIUS_COMMON_PW
:
   'radius-common-pw'
;

RADIUS_SERVER
:
   'radius-server'
;

RANDOM
:
   'random'
;

RANDOM_DETECT
:
   'random-detect'
;

RANGE
:
   'range'
;

RATE_LIMIT
:
   'rate-limit'
;

RATE_MODE
:
   'rate-mode'
;

RC4_SHA1
:
   'rc4-sha1'
;

RCMD
:
   'rcmd'
;

RCV_QUEUE
:
   'rcv-queue'
;

RD
:
   'rd'
;

REACT
:
   'react'
;

REACTION
:
   'reaction'
;

REAL
:
   'real'
;

RECEIVE
:
   'receive'
;

RECORD
:
   'record'
;

RECORD_ENTRY
:
   'record-entry'
;

RED
:
   'red'
;

REDIRECT
:
   'redirect'
;

REDIRECT_FQDN
:
   'redirect-fqdn'
;

REDIRECTS
:
   'redirects'
;

REDISTRIBUTE
:
   'redistribute'
;

REDISTRIBUTE_INTERNAL
:
   'redistribute-internal'
;

REDUNDANCY
:
   'redundancy'
;

REFERENCE_BANDWIDTH
:
   'reference-bandwidth'
;

REFLECT
:
   'reflect'
;

REFLEXIVE_LIST
:
   'reflexive-list'
;

REGEX_MODE
:
   'regex-mode'
;

REGISTER_RATE_LIMIT
:
   'register-rate-limit'
;

REGISTER_SOURCE
:
   'register-source'
;

RELAY
:
   'relay'
;

RELOAD
:
   'reload'
;

RELOAD_DELAY
:
   'reload-delay'
;

RELOAD_TYPE
:
   'reload-type'
;

REMARK
:
   'remark' -> pushMode ( M_REMARK )
;

REMOTE_AS
:
   'remote-as'
;

REMOTE_IP
:
   'remote-ip'
;

REMOTE_PORT
:
   'remote-port'
;

REMOVE_PRIVATE_AS
:
   'remove-private-as'
;

REMOVE_PRIVATE_CAP_A_CAP_S
:
   'remove-private-AS'
;

REMOTE_SERVER
:
   'remote-server'
;

REMOTE_SPAN
:
   'remote-span'
;

REMOVED
:
   '<removed>'
;

REPLACE_AS
:
   'replace-as'
;

REPLY_TO
:
   'reply-to'
;

REOPTIMIZE
:
   'reoptimize'
;

REQUEST
:
   'request'
;

REQUEST_DATA_SIZE
:
   'request-data-size'
;

RESOURCE
:
   'resource'
;

RESOURCE_POOL
:
   'resource-pool'
;

RESOURCES
:
   'resources'
;

RETRANSMIT
:
   'retransmit'
;

RETRANSMIT_TIMEOUT
:
   'retransmit-timeout'
;

RETRIES
:
   'retries'
;

REVERSE_ROUTE
:
   'reverse-route'
;

REVISION
:
   'revision'
;

REVOCATION_CHECK
:
   'revocation-check'
;

RFC1583COMPATIBILITY
:
   'rfc1583compatibility'
;

RIB_HAS_ROUTE
:
   'rib-has-route'
;

RIB_METRIC_AS_EXTERNAL
:
   'rib-metric-as-external'
;

RIB_METRIC_AS_INTERNAL
:
   'rib-metric-as-internal'
;

RING
:
   'ring'
;

RIP
:
   'rip'
;

RMON
:
   'rmon'
;

ROLE
:
   'role'
;

ROOT
:
   'root'
;

ROTARY
:
   'rotary'
;

ROUTE
:
   'route'
;

ROUTE_CACHE
:
   'route-cache'
;

ROUTE_MAP
:
   'route-map'
;

ROUTE_ONLY
:
   'route-only'
;

ROUTE_POLICY
:
   'route-policy'
;

ROUTE_REFLECTOR_CLIENT
:
   'route-reflector-client'
;

ROUTE_TARGET
:
   'route-target'
;

ROUTER
:
   'router'
;

ROUTER_ADVERTISEMENT
:
   'router-advertisement'
;

ROUTER_ID
:
   'router-id'
;

ROUTER_INTERFACE
:
   'router-interface'
;

ROUTER_SOLICITATION
:
   'router-solicitation'
;

ROUTING
:
   'routing'
;

RPF_VECTOR
:
   'rpf-vector'
;

RP_ADDRESS
:
   'rp-address'
;

RP_ANNOUNCE_FILTER
:
   'rp-announce-filter'
;

RP_CANDIDATE
:
   'rp-candidate'
;

RP_LIST
:
   'rp-list'
;

RSAKEYPAIR
:
   'rsakeypair'
;

RTR
:
   'rtr'
;

RST
:
   'rst'
;

RSVP
:
   'rsvp'
;

RT
:
   'rt'
;

RULE
:
   'rule' -> pushMode ( M_Rule )
;

SAME_SECURITY_TRAFFIC
:
   'same-security-traffic'
;

SAMPLER
:
   'sampler'
;

SAMPLER_MAP
:
   'sampler-map'
;

SAP
:
   'sap'
;

SA_FILTER
:
   'sa-filter'
;

SCAN_TIME
:
   'scan-time'
;

SCCP
:
   'sccp'
;

SCHEDULE
:
   'schedule'
;

SCHEDULER
:
   'scheduler'
;

SCHEME
:
   'scheme'
;

SCOPE
:
   'scope'
;

SCP
:
   'scp'
;

SCRIPTING
:
   'scripting'
;

SCTP
:
   'sctp'
;

SDM
:
   'sdm'
;

SDR
:
   'sdr'
;

SECONDARY
:
   'secondary'
;

SECRET
:
   'secret'
;

SECUREID_UDP
:
   'secureid-udp'
;

SECURITY
:
   'security'
;

SECURITY_LEVEL
:
   'security-level'
;

SELF
:
   'self'
;

SEND
:
   'send'
;

SEND_COMMUNITY
:
   'send-community'
;

SEND_COMMUNITY_EBGP
:
   'send-community-ebgp'
;

SEND_EXTENDED_COMMUNITY_EBGP
:
   'send-extended-community-ebgp'
;

SEND_LABEL
:
   'send-label'
;

SEND_LIFETIME
:
   'send-lifetime'
;

SEND_RP_ANNOUNCE
:
   'send-rp-announce'
;

SEND_RP_DISCOVERY
:
   'send-rp-discovery'
;

SENDER
:
   'sender'
;

SEQ
:
   'seq'
;

SEQUENCE
:
   'sequence'
;

SERIAL
:
   'serial'
;

SERIAL_NUMBER
:
   'serial-number'
;

SERVE
:
   'serve'
;

SERVE_ONLY
:
   'serve-only'
;

SERVER
:
   'server'
;

SERVERFARM
:
   'serverfarm'
;

SERVER_PRIVATE
:
   'server-private'
;

SERVER_TYPE
:
   'server-type'
;

SERVICE
:
   'service'
;

SERVICE_CLASS
:
   'service-class'
;

SERVICE_MODULE
:
   'service-module'
;

SERVICE_OBJECT
:
   'service-object'
;

SERVICE_POLICY
:
   'service-policy'
;

SERVICE_QUEUE
:
   'service-queue'
;

SERVICE_TYPE
:
   'service-type'
;

SESSION_DISCONNECT_WARNING
:
   'session-disconnect-warning' -> pushMode ( M_COMMENT )
;

SESSION_ID
:
   'session-id'
;

SESSION_LIMIT
:
   'session-limit'
;

SESSION_PROTECTION
:
   'session-protection'
;

SESSION_TIMEOUT
:
   'session-timeout'
;

SET
:
   'set'
;

SET_COLOR
:
   'set-color'
;

SET_OVERLOAD_BIT
:
   'set-overload-bit'
;

SETUP
:
   'setup'
;

SEVERITY
:
   'severity'
;

SFLOW
:
   'sflow'
;

SGBP
:
   'sgbp'
;

SHA1
:
   'sha1' -> pushMode ( M_SHA1 )
;

SHAPE
:
   'shape'
;

SHELL
:
   'shell'
;

SHORT_TXT
:
   'short-txt'
;

SHUT
:
   'shut'
;

SHUTDOWN
:
   'shutdown'
;

SIGNAL
:
   'signal'
;

SIGNALLED_BANDWIDTH
:
   'signalled-bandwidth'
;

SIGNALLED_NAME
:
   'signalled-name'
;

SINGLE_CONNECTION
:
   'single-connection'
;

SINGLE_ROUTER_MODE
:
   'single-router-mode'
;

SINGLE_TOPOLOGY
:
   'single-topology'
;

SITE_ID
:
   'site-id'
;

SLA
:
   'sla'
;

SLOT
:
   'slot'
;

SMTP
:
   'smtp'
;

SMTP_SERVER
:
   'smtp-server'
;

SNMP
:
   'snmp'
;

SNMP_SERVER
:
   'snmp-server'
;

SNMPTRAP
:
   'snmptrap'
;

SNOOPING
:
   'snooping'
;

SNP
:
   'snp'
;

SNTP
:
   'sntp'
;

SORT_BY
:
   'sort-by'
;

SPE
:
   'spe'
;

SPF_INTERVAL
:
   'spf-interval'
;

SOFT_PREEMPTION
:
   'soft-preemption'
;

SOFT_RECONFIGURATION
:
   'soft-reconfiguration'
;

SONET
:
   'sonet'
;

SOURCE
:
   'source'
;

SOURCE_INTERFACE
:
   'source-interface'
;

SOURCE_IP_ADDRESS
:
   'source-ip-address'
;

SOURCE_ROUTE
:
   'source-route'
;

SOURCE_QUENCH
:
   'source-quench'
;

SPANNING_TREE
:
   'spanning-tree'
;

SPD
:
   'spd'
;

SPEED
:
   'speed'
;

SPLIT_TUNNEL_NETWORK_LIST
:
   'split-tunnel-network-list'
;

SPLIT_TUNNEL_POLICY
:
   'split-tunnel-policy'
;

SPT_THRESHOLD
:
   'spt-threshold'
;

SQLNET
:
   'sqlnet'
;

SRLG
:
   'srlg'
;

SRR_QUEUE
:
   'srr-queue'
;

SSH
:
   'ssh'
;

SSH_CERTIFICATE
:
   'ssh-certificate'
;

SSH_KEYDIR
:
   'ssh_keydir'
;

SSH_PUBLICKEY
:
   'ssh-publickey'
;

SSID
:
   'ssid'
;

SSL
:
   'ssl'
;

SSM
:
   'ssm'
;

STACK_MAC
:
   'stack-mac'
;

STACK_MIB
:
   'stack-mib'
;

STACK_UNIT
:
   'stack-unit'
;

STANDARD
:
   'standard'
   { enableDEC = true; enableACL_NUM = false; }

;

STANDBY
:
   'standby'
;

START_STOP
:
   'start-stop'
;

START_TIME
:
   'start-time'
;

STATE_REFRESH
:
   'state-refresh'
;

STATIC
:
   'static'
;

STATION_ROLE
:
   'station-role'
;

STATISTICS
:
   'statistics'
;

STCAPP
:
   'stcapp'
;

STICKY
:
   'sticky'
;

STOP
:
   'stop'
;

STOP_ONLY
:
   'stop-only'
;

STOP_RECORD
:
   'stop-record'
;

STOPBITS
:
   'stopbits'
;

STORM_CONTROL
:
   'storm-control'
;

STP
:
   'stp'
;

STREET_ADDRESS
:
   'street-address'
;

STREETADDRESS
:
   'streetaddress' -> pushMode ( M_Description )
;

STRING
:
   'string'
;

STUB
:
   'stub'
;

SUBJECT_NAME
:
   'subject-name'
;

SUBNET
:
   'subnet'
;

SUBNETS
:
   'subnets'
;

SUBNET_ZERO
:
   'subnet-zero'
;

SUBSCRIBE_TO
:
   'subscribe-to'
;

SUBSCRIBE_TO_ALERT_GROUP
:
   'subscribe-to-alert-group'
;

SUBSCRIBER
:
   'subscriber'
;

SUCCESS
:
   'success'
;

SUMMARY_ADDRESS
:
   'summary-address'
;

SUMMARY_ONLY
:
   'summary-only'
;

SUNRPC
:
   'sunrpc'
;

SUPPLEMENTARY_SERVICES
:
   'supplementary-services'
;

SUPPRESS
:
   'suppress'
;

SUPPRESS_FIB_PENDING
:
   'suppress-fib-pending'
;

SVC
:
   'svc'
;

SVCLC
:
   'svclc'
;

SWITCH
:
   'switch'
;

SWITCH_PROFILE
:
   'switch-profile'
;

SWITCH_TYPE
:
   'switch-type'
;

SWITCHBACK
:
   'switchback'
;

SWITCHING_MODE
:
   'switching-mode'
;

SWITCHNAME
:
   'switchname'
;

SWITCHPORT
:
   'switchport'
;

SYN
:
   'syn'
;

SYNC
:
   'sync'
;

SYNCHRONIZATION
:
   'synchronization'
;

SYSLOG
:
   'syslog'
;

SYSLOGD
:
   'syslogd'
;

SYSOPT
:
   'sysopt'
;

SYSTEM
:
   'system'
;

SYSTEM_INIT
:
   'system-init'
;

SYSTEM_MAX
:
   'system-max'
;

SYSTEM_PRIORITY
:
   'system-priority'
;

TABLE_MAP
:
   'table-map'
;

TACACS
:
   'tacacs'
;

TACACS_DS
:
   'tacacs-ds'
;

TACACS_PLUS
:
   'tacacs+'
;

TACACS_PLUS_ASA
:
   'TACACS+'
;

TACACS_SERVER
:
   'tacacs-server'
;

TAG
:
   'tag'
;

TAG_SWITCHING
:
   'tag-switching'
;

TAGGED
:
   'tagged'
;

TALK
:
   'talk'
;

TAP
:
   'tap'
;

TASK
:
   'task'
;

TASK_SPACE_EXECUTE
:
   'task execute'
;

TASKGROUP
:
   'taskgroup'
;

TB_VLAN1
:
   'tb-vlan1'
;

TB_VLAN2
:
   'tb-vlan2'
;

TCAM
:
   'tcam'
;

TCP
:
   'tcp'
;

TCP_CONNECT
:
   'tcp-connect'
;

TCP_UDP
:
   'tcp-udp'
;

TELNET
:
   'telnet'
;

TEMPLATE
:
   'template'
;

TERMINAL
:
   'terminal'
;

TERMINAL_TYPE
:
   'terminal-type'
;

TFTP
:
   'tftp'
;

TFTP_SERVER
:
   'tftp-server'
;

THEN
:
   'then'
;

THREAT_DETECTION
:
   'threat-detection'
;

THREE_DES
:
   '3des'
;

THREE_DES_SHA1
:
   '3des-sha1'
;

THRESHOLD
:
   'threshold'
;

TIME
:
   'time'
;

TIME_EXCEEDED
:
   'time-exceeded'
;

TIME_RANGE
:
   'time-range'
;

TIMEOUT
:
   'timeout'
;

TIMEOUTS
:
   'timeouts'
;

TIMER
:
   'timer'
;

TIMERS
:
   'timers'
;

TIMESTAMP
:
   'timestamp'
;

TIMESTAMP_REPLY
:
   'timestamp-reply'
;

TIMESTAMP_REQUEST
:
   'timestamp-request'
;

TIMING
:
   'timing'
;

TLS_PROXY
:
   'tls-proxy'
;

TM_VOQ_COLLECTION
:
   'tm-voq-collection'
;

TOOL
:
   'tool'
;

TOP
:
   'top'
;

TOS
:
   'tos'
;

TRACE
:
   'trace'
;

TRACEROUTE
:
   'traceroute'
;

TRACK
:
   'track'
;

TRACKED
:
   'tracked'
;

TRACKING_PRIORITY_INCREMENT
:
   'tracking-priority-increment'
;

TRAFFIC_ENG
:
   'traffic-eng'
;

TRANSCEIVER
:
   'transceiver'
;

TRANSLATE
:
   'translate'
;

TRANSMIT
:
   'transmit'
;

TRANSPORT
:
   'transport'
;

TRIGGER
:
   'trigger'
;

TRUNK
:
   'trunk'
;

TRUST
:
   'trust'
;

TRUSTED_KEY
:
   'trusted-key'
;

TRUSTPOINT
:
   'trustpoint'
;

TRUSTPOOL
:
   'trustpool'
;

TTL
:
   'ttl'
;

TTL_EXCEEDED
:
   'ttl-exceeded'
;

TTL_THRESHOLD
:
   'ttl-threshold'
;

TUNNEL
:
   'tunnel'
;

TUNNEL_GROUP
:
   'tunnel-group'
;

TUNNEL_GROUP_LIST
:
   'tunnel-group-list'
;

TUNNEL_ID
:
   'tunnel-id'
;

TX_QUEUE
:
   'tx-queue'
;

TYPE
:
   'type'
;

TYPE_1
:
   'type-1'
;

TYPE_2
:
   'type-2'
;

UC_TX_QUEUE
:
   'uc-tx-queue'
;

UDF
:
   'udf'
;

UDLD
:
   'udld'
;

UDP
:
   'udp'
;

UDP_JITTER
:
   'udp-jitter'
;

UID
:
   'uid'
;

UNABLE
:
   'Unable'
;

UNICAST_ROUTING
:
   'unicast-routing'
;

UNIQUE
:
   'unique'
;

UNIT
:
   'unit'
;

UNNUMBERED
:
   'unnumbered'
;

UNREACHABLE
:
   'unreachable'
;

UNREACHABLES
:
   'unreachables'
;

UNICAST
:
   'unicast'
;

UNTAGGED
:
   'untagged'
;

UPDATE
:
   'update'
;

UPDATE_CALENDAR
:
   'update-calendar'
;

UPDATE_SOURCE
:
   'update-source' -> pushMode ( M_Interface )
;

UPGRADE
:
   'upgrade'
;

URG
:
   'urg'
;

URL_LIST
:
   'url-list'
;

USE
:
   'use'
;

USE_VRF
:
   'use-vrf'
;

USER
:
   'user'
;

USER_IDENTITY
:
   'user-identity'
;

USER_MESSAGE
:
   'user-message' -> pushMode ( M_Description )
;

USER_STATISTICS
:
   'user-statistics'
;

USERGROUP
:
   'usergroup'
;

USERNAME
:
   'username'
;

USERNAME_PROMPT
:
   'username-prompt'
;

USERS
:
   'users'
;

UUCP
:
   'uucp'
;

V1_RP_REACHABILITY
:
   'v1-rp-reachability'
;

V4
:
   'v4'
;

V6
:
   'v6'
;

VAD
:
   'vad'
;

VALIDATION_USAGE
:
   'validation-usage'
;

VDC
:
   'vdc'
;

VER
:
   'ver'
;

VERIFY
:
   'verify'
;

VERSION
:
   'version'
;

VIOLATE_ACTION
:
   'violate-action'
;

VIRTUAL
:
   'virtual'
;

VIRTUAL_ADDRESS
:
   'virtual-address'
;

VIRTUAL_REASSEMBLY
:
   'virtual-reassembly'
;

VIRTUAL_ROUTER
:
   'virtual-router'
;

VIRTUAL_TEMPLATE
:
   'virtual-template'
;

VLAN
:
   'vlan'
;

VLT
:
   'vlt'
;

VLT_PEER_LAG
:
   'vlt-peer-lag'
;

VM_CPU
:
   'vm-cpu'
;

VM_MEMORY
:
   'vm-memory'
;

VMPS
:
   'vmps'
;

VOICE
:
   'voice'
;

VOICE_CARD
:
   'voice-card'
;

VOICE_PORT
:
   'voice-port'
;

VPC
:
   'vpc'
;

VPDN
:
   'vpdn'
;

VPDN_GROUP
:
   'vpdn-group'
;

VPLS
:
   'vpls'
;

VPN
:
   'vpn'
;

VPNV4
:
   'vpnv4'
;

VPNV6
:
   'vpnv6'
;

VPN_FILTER
:
   'vpn-filter'
;

VPN_GROUP_POLICY
:
   'vpn-group-policy'
;

VPN_IDLE_TIMEOUT
:
   'vpn-idle-timeout'
;

VPN_SESSION_TIMEOUT
:
   'vpn-session-timeout'
;

VPN_SIMULTANEOUS_LOGINS
:
   'vpn-simultaneous-logins'
;

VPN_TUNNEL_PROTOCOL
:
   'vpn-tunnel-protocol'
;

VRF
:
   'vrf'
   {enableIPV6_ADDRESS = false;}

;

VRF_ALSO
:
   'vrf-also'
;

VRRP
:
   'vrrp'
;

VRRP_GROUP
:
   'vrrp-group'
;

VSERVER
:
   'vserver'
;

VTP
:
   'vtp'
;

VTY_POOL
:
   'vty-pool'
;

WAIT_START
:
   'wait-start'
;

WEBVPN
:
   'webvpn'
;

WEIGHT
:
   'weight'
;

WEIGHTING
:
   'weighting'
;

WHO
:
   'who'
;

WHOIS
:
   'whois'
;

WIDE
:
   'wide'
;

WINDOW_SIZE
:
   'window-size'
;

WINS_SERVER
:
   'wins-server'
;

WISM
:
   'wism'
;

WITHOUT_CSD
:
   'without-csd'
;

WLAN
:
   'wlan'
;

WRED
:
   'wred'
;

WRED_PROFILE
:
   'wred-profile'
;

WRR_QUEUE
:
   'wrr-queue'
;

WSMA
:
   'wsma'
;

WWW
:
   'www'
;

X25
:
   'x25'
;

X29
:
   'x29'
;

XCONNECT
:
   'xconnect'
;

XDMCP
:
   'xdmcp'
;

XDR
:
   'xdr'
;

XLATE
:
   'xlate'
;

XML
:
   'xml'
;

XML_CONFIG
:
   'xml-config'
;

YELLOW
:
   'yellow'
;

// Other Tokens

MULTICONFIGPART
:
   '############ MultiConfigPart' F_NonNewline* F_Newline+ -> channel ( HIDDEN
   )
;

COMMUNITY_NUMBER
:
   F_Digit
   {!enableIPV6_ADDRESS}?

   F_Digit* ':' F_Digit+
;

MAC_ADDRESS_LITERAL
:
   F_HexDigit F_HexDigit F_HexDigit F_HexDigit '.' F_HexDigit F_HexDigit
   F_HexDigit F_HexDigit '.' F_HexDigit F_HexDigit F_HexDigit F_HexDigit
;

VARIABLE
:
   (
      (
         F_Variable_RequiredVarChar
         (
            (
               {!enableIPV6_ADDRESS}?

               F_Variable_VarChar*
            )
            |
            (
               {enableIPV6_ADDRESS}?

               F_Variable_VarChar_Ipv6*
            )
         )
      )
      |
      (
         (
            F_Variable_VarChar
            {!enableIPV6_ADDRESS}?

            F_Variable_VarChar* F_Variable_RequiredVarChar F_Variable_VarChar*
         )
         |
         (
            F_Variable_VarChar_Ipv6
            {enableIPV6_ADDRESS}?

            F_Variable_VarChar_Ipv6* F_Variable_RequiredVarChar
            F_Variable_VarChar_Ipv6*
         )
      )
   )
   {
      if (enableACL_NUM) {
         enableACL_NUM = false;
         enableDEC = true;
      }
      if (enableCOMMUNITY_LIST_NUM) {
         enableCOMMUNITY_LIST_NUM = false;
         enableDEC = true;
      }
   }

;

ACL_NUM
:
   F_Digit
   {enableACL_NUM}?

   F_Digit*
   {
	int val = Integer.parseInt(getText());
	if ((1 <= val && val <= 99) || (1300 <= val && val <= 1999)) {
		_type = ACL_NUM_STANDARD;
	}
	else if ((100 <= val && val <= 199) || (2000 <= val && val <= 2699)) {
		_type = ACL_NUM_EXTENDED;
	}
	else if (200 <= val && val <= 299) {
		_type = ACL_NUM_PROTOCOL_TYPE_CODE;
	}
	else if (600 <= val && val <= 699) {
		_type = ACL_NUM_APPLETALK;
	}
   else if (700 <= val && val <= 799) {
      _type = ACL_NUM_MAC;
   }
	else if (800 <= val && val <= 899) {
		_type = ACL_NUM_IPX;
	}
	else if (900 <= val && val <= 999) {
		_type = ACL_NUM_EXTENDED_IPX;
	}
	else if (1000 <= val && val <= 1099) {
		_type = ACL_NUM_IPX_SAP;
	}
	else {
		_type = ACL_NUM_OTHER;
	}
	enableDEC = true;
	enableACL_NUM = false;
}

;

AMPERSAND
:
   '&'
;

ANGLE_BRACKET_LEFT
:
   '<'
;

ANGLE_BRACKET_RIGHT
:
   '>'
;

ASTERISK
:
   '*'
;

AT
:
   '@'
;

BACKSLASH
:
   '\\'
;

BLANK_LINE
:
   (
      F_Whitespace
   )* F_Newline
   {lastTokenType == NEWLINE}?

   F_Newline* -> channel ( HIDDEN )
;

BRACE_LEFT
:
   '{'
;

BRACE_RIGHT
:
   '}'
;

BRACKET_LEFT
:
   '['
;

BRACKET_RIGHT
:
   ']'
;

CARAT
:
   '^'
;

COLON
:
   ':'
;

COMMA
:
   ','
;

COMMUNITY_LIST_NUM
:
   F_Digit
   {enableCOMMUNITY_LIST_NUM}?

   F_Digit*
   {
		int val = Integer.parseInt(getText());
		if (1 <= val && val <= 99) {
			_type = COMMUNITY_LIST_NUM_STANDARD;
		}
		else if (100 <= val && val <= 500) {
			_type = COMMUNITY_LIST_NUM_EXPANDED;
		}
		enableCOMMUNITY_LIST_NUM = false;
		enableDEC = true;
	}

;

COMMUNITY_SET_REGEX
:
   '\'' ~[':&<> ]* ':' ~[':&<> ]* '\''
;

COMMENT_LINE
:
   (
      F_Whitespace
   )* '!'
   {lastTokenType == NEWLINE}?

   F_NonNewline* F_Newline+ -> channel ( HIDDEN )
;

COMMENT_TAIL
:
   '!' F_NonNewline* -> channel ( HIDDEN )
;

DASH
:
   '-'
;

DOLLAR
:
   '$'
;

DEC
:
   F_Digit
   {enableDEC}?

   F_Digit*
;

DIGIT
:
   F_Digit
;

DOUBLE_QUOTE
:
   '"'
;

EQUALS
:
   '='
;

ESCAPE_C
:
   (
      '^C'
      | '\u0003'
      | '#'
   )
;

FLOAT
:
   (
      F_PositiveDigit* F_Digit '.' F_Digit+
   )
;

FORWARD_SLASH
:
   '/'
;

HEX
:
   '0x' F_HexDigit+
;

IP_ADDRESS
:
   F_DecByte '.'
   {enableIP_ADDRESS}?

   F_DecByte '.' F_DecByte '.' F_DecByte
;

IP_PREFIX
:
   F_DecByte '.'
   {enableIP_ADDRESS}?

   F_DecByte '.' F_DecByte '.' F_DecByte '/' F_Digit F_Digit?
;

IPV6_ADDRESS
:
   (
      (
         ':'
         {enableIPV6_ADDRESS}?

         ':'
         (
            (
               F_HexDigit+ ':'
            )* F_HexDigit+
         )?
      )
      |
      (
         F_HexDigit+
         {enableIPV6_ADDRESS}?

         ':' ':'?
      )+
   )
   (
      F_HexDigit+
   )?
;

IPV6_PREFIX
:
   (
      (
         ':'
         {enableIPV6_ADDRESS}?

         ':'
         (
            (
               F_HexDigit+ ':'
            )* F_HexDigit+
         )?
      )
      |
      (
         F_HexDigit+
         {enableIPV6_ADDRESS}?

         ':' ':'?
      )+
      (
         F_HexDigit+
      )?
   ) '/' F_DecByte
;

NEWLINE
:
   F_Newline+
   {
      if (!inCommunitySet) {
   	  enableIPV6_ADDRESS = true;
   	}
   	enableIP_ADDRESS = true;
  }

;

PAREN_LEFT
:
   '('
;

PAREN_RIGHT
:
   ')'
;

PERCENT
:
   '%'
;

PERIOD
:
   '.'
;

PLUS
:
   '+'
;

POUND
:
   '#'
;

RP_VARIABLE
:
   '$' F_Variable_RequiredVarChar F_Variable_VarChar_Ipv6*
;

SEMICOLON
:
   ';'
;

SINGLE_QUOTE
:
   '\''
;

UNDERSCORE
:
   '_'
;

WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

// Fragments

fragment
F_Dec16
:
   (
      F_PositiveDigit F_Digit F_Digit F_Digit F_Digit
   )
   |
   (
      F_PositiveDigit F_Digit F_Digit F_Digit
   )
   |
   (
      F_PositiveDigit F_Digit F_Digit
   )
   |
   (
      F_PositiveDigit F_Digit
   )
   | F_Digit
;

fragment
F_DecByte
:
   (
      F_PositiveDigit F_Digit F_Digit
   )
   |
   (
      F_PositiveDigit F_Digit
   )
   | F_Digit
;

fragment
F_Digit
:
   '0' .. '9'
;

fragment
F_HexDigit
:
   (
      '0' .. '9'
      | 'a' .. 'f'
      | 'A' .. 'F'
   )
;

fragment
F_HexWord
:
   F_HexDigit F_HexDigit F_HexDigit F_HexDigit
;

fragment
F_Letter
:
   F_LowerCaseLetter
   | F_UpperCaseLetter
;

fragment
F_LowerCaseLetter
:
   'a' .. 'z'
;

fragment
F_Newline
:
   [\n\r]
;

fragment
F_NonNewline
:
   ~[\n\r]
;

fragment
F_NonWhitespace
:
   ~( ' ' | '\t' | '\u000C' | '\n' | '\r' )
;

F_PositiveHexDigit
:
   (
      '1' .. '9'
      | 'a' .. 'f'
      | 'A' .. 'F'
   )
;

fragment
F_PositiveDigit
:
   '1' .. '9'
;

fragment
F_UpperCaseLetter
:
   'A' .. 'Z'
;

fragment
F_Variable_RequiredVarChar
:
   ~( '0' .. '9' | '-' | [ \t\n\r(),!+$'*] | '[' | ']' | [/.] | ':' )
;

fragment
F_Variable
:
   F_Variable_VarChar* F_Variable_RequiredVarChar F_Variable_VarChar*
;

fragment
F_Variable_VarChar
:
   ~( [ \t\n\r(),!+$'*] | '[' | ']' )
;

fragment
F_Variable_VarChar_Ipv6
:
   ~( [ \t\n\r(),!+$'*] | '[' | ']' | ':' )
;

fragment
F_Whitespace
:
   ' '
   | '\t'
   | '\u000C'
;

mode M_AsPath;

M_AsPath_ACCESS_LIST
:
   'access-list' -> type ( ACCESS_LIST ) , mode ( M_AsPathAccessList )
;

M_AsPath_DEC
:
   F_Digit+ -> type ( DEC ) , popMode
;

M_AsPath_IN
:
   'in' -> type ( IN ) , popMode
;

M_AsPath_PASSES_THROUGH
:
   'passes-through' -> type ( PASSES_THROUGH ) , popMode
;

M_AsPath_PREPEND
:
   'prepend' -> type ( PREPEND ) , popMode
;

M_AsPath_ORIGINATES_FROM
:
   'originates-from' -> type ( ORIGINATES_FROM ) , popMode
;

M_AsPath_REGEX_MODE
:
   'regex-mode' -> type ( REGEX_MODE ) , popMode
;

M_AsPath_TAG
:
   'tag' -> type ( TAG ) , popMode
;

M_AsPath_VARIABLE
:
   F_Variable_RequiredVarChar F_Variable_VarChar* -> type ( VARIABLE ) ,
   popMode
;

M_AsPath_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_AsPathAccessList;

M_AsPathAccessList_DEC
:
   F_Digit+ -> type ( DEC )
;

M_AsPathAccessList_DENY
:
   'deny' -> type ( DENY ) , mode ( M_AsPathRegex )
;

M_AsPathAccessList_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , mode ( DEFAULT_MODE )
;

M_AsPathAccessList_PERMIT
:
   'permit' -> type ( PERMIT ) , mode ( M_AsPathRegex )
;

M_AsPathAccessList_SEQ
:
   'seq' -> type ( SEQ )
;

M_AsPathAccessList_VARIABLE
:
   F_Variable_RequiredVarChar F_Variable_VarChar* -> type ( VARIABLE )
;

M_AsPathAccessList_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_AsPathRegex;

M_AsPathRegex_ANY
:
   'any' -> type ( ANY )
;

M_AsPathRegex_ASTERISK
:
   '*' -> type ( ASTERISK )
;

M_AsPathRegex_BRACKET_LEFT
:
   '[' -> type ( BRACKET_LEFT )
;

M_AsPathRegex_BRACKET_RIGHT
:
   ']' -> type ( BRACKET_RIGHT )
;

M_AsPathRegex_CARAT
:
   '^' -> type ( CARAT )
;

M_AsPathRegex_DASH
:
   '-' -> type ( DASH )
;

M_AsPathRegex_DEC
:
   F_Digit+ -> type ( DEC )
;

M_AsPathRegex_DOLLAR
:
   '$' -> type ( DOLLAR )
;

M_AsPathRegex_DOUBLE_QUOTE
:
   '"' -> channel ( HIDDEN )
;

M_AsPathRegex_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_AsPathRegex_PAREN_LEFT
:
   '(' -> type ( PAREN_LEFT )
;

M_AsPathRegex_PAREN_LEFT_LITERAL
:
   '\\(' -> type ( PAREN_LEFT_LITERAL )
;

M_AsPathRegex_PAREN_RIGHT
:
   ')' -> type ( PAREN_RIGHT )
;

M_AsPathRegex_PAREN_RIGHT_LITERAL
:
   '\\)' -> type ( PAREN_RIGHT_LITERAL )
;

M_AsPathRegex_PERIOD
:
   '.' -> type ( PERIOD )
;

M_AsPathRegex_PIPE
:
   '|' -> type ( PIPE )
;

M_AsPathRegex_PLUS
:
   '+' -> type ( PLUS )
;

M_AsPathRegex_UNDERSCORE
:
   '_' -> channel ( HIDDEN )
;

M_AsPathRegex_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_Authentication;

M_Authentication_BANNER
:
   'banner' -> type ( BANNER ) , mode ( M_BANNER )
;

M_Authentication_ARAP
:
   'arap' -> type ( ARAP ) , popMode
;

M_Authentication_ATTEMPTS
:
   'attempts' -> type ( ATTEMPTS ) , popMode
;

M_Authentication_DOT1X
:
   'dot1x' -> type ( DOT1X ) , popMode
;

M_Authentication_ENABLE
:
   'enable' -> type ( ENABLE ) , popMode
;

M_Authentication_EOU
:
   'eou' -> type ( EOU ) , popMode
;

M_Authentication_FAIL_MESSAGE
:
   'fail-message' -> type ( FAIL_MESSAGE ) , popMode
;

M_Authentication_FAILURE
:
   'failure' -> type ( FAILURE ) , popMode
;

M_Authentication_HTTP
:
   'http' -> type ( HTTP ) , popMode
;

M_Authentication_INCLUDE
:
   'include' -> type ( INCLUDE ) , popMode
;

M_Authentication_LOGIN
:
   'login' -> type ( LOGIN ) , popMode
;

M_Authentication_MESSAGE_DIGEST
:
   'message-digest' -> type ( MESSAGE_DIGEST ) , popMode
;

M_Authentication_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_Authentication_ONEP
:
   'onep' -> type ( ONEP ) , popMode
;

M_Authentication_PASSWORD_PROMPT
:
   'password-prompt' -> type ( PASSWORD_PROMPT ) , popMode
;

M_Authentication_PPP
:
   'ppp' -> type ( PPP ) , popMode
;

M_Authentication_SGBP
:
   'sgbp' -> type ( SGBP ) , popMode
;

M_Authentication_SERIAL
:
   'serial' -> type ( SERIAL ) , popMode
;

M_Authentication_SSH
:
   'ssh' -> type ( SSH ) , popMode
;

M_Authentication_SUCCESS
:
   'success' -> type ( SUCCESS ) , popMode
;

M_Authentication_SUPPRESS
:
   'suppress' -> type ( SUPPRESS ) , popMode
;

M_Authentication_TELNET
:
   'telnet' -> type ( TELNET ) , popMode
;

M_Authentication_USERNAME_PROMPT
:
   'username-prompt' -> type ( USERNAME_PROMPT ) , popMode
;

M_Authentication_VARIABLE
:
   F_Variable -> type ( VARIABLE ) , popMode
;

M_Authentication_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_BANNER;

M_BANNER_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

M_BANNER_ESCAPE_C
:
   (
      '^C'
      |
      (
         '^' F_Newline+
      )
      | '\u0003'
   ) -> type ( ESCAPE_C ) , mode ( M_MOTD_C )
;

M_BANNER_HASH
:
   '#' -> type ( POUND ) , mode ( M_MOTD_HASH )
;

M_BANNER_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , mode ( M_MOTD_EOF )
;

mode M_Certificate;

M_Certificate_CA
:
   'ca' -> type ( CA ) , pushMode ( M_CertificateText )
;

M_Certificate_CHAIN
:
   'chain' -> type ( CHAIN ) , popMode
;

M_Certificate_SELF_SIGNED
:
   'self-signed' -> type ( SELF_SIGNED ) , pushMode ( M_CertificateText )
;

M_Cerficate_HEX_FRAGMENT
:
   [A-Fa-f0-9]+ -> type ( HEX_FRAGMENT ) , pushMode ( M_CertificateText )
;

M_Certificate_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_CertificateText;

M_CertificateText_QUIT
:
   'quit' -> type ( QUIT ) , mode ( DEFAULT_MODE )
;

M_CertificateText_HEX_FRAGMENT
:
   [A-Fa-f0-9]+ -> type ( HEX_FRAGMENT )
;

M_CertificateText_WS
:
   (
      F_Whitespace
      | F_Newline
   )+ -> channel ( HIDDEN )
;

mode M_COMMENT;

M_COMMENT_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_COMMENT_NON_NEWLINE
:
   F_NonNewline+
;

mode M_DES;

M_DES_DEC_PART
:
   F_Digit+
;

M_DES_HEX_PART
:
   F_HexDigit+ -> popMode
;

M_DES_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_DES_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_Description;

M_Description_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_Description_NON_NEWLINE
:
   F_NonNewline+ -> type ( RAW_TEXT )
;

mode M_Execute;

M_Execute_TEXT
:
   ~'}'+
;

M_Execute_BRACE_RIGHT
:
   '}' -> type ( BRACE_RIGHT ) , popMode
;

mode M_Extcommunity;

M_Extcommunity_COLON
:
   ':' -> type ( COLON )
;

M_Extcommunity_DEC
:
   F_Digit+ -> type ( DEC )
;

M_ExtCommunity_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_Extcommunity_RT
:
   'rt' -> type ( RT )
;

M_Extcommunity_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_Interface;

M_Interface_ALL
:
   'all' -> type ( ALL ) , popMode
;

M_Interface_BREAKOUT
:
   'breakout' -> type ( BREAKOUT ) , popMode
;

M_Interface_DOLLAR
:
   '$' -> type ( DOLLAR ) , popMode
;

M_Interface_IP
:
   'ip' -> type ( IP ) , popMode
;

M_Interface_POINT_TO_POINT
:
   'point-to-point' -> type ( POINT_TO_POINT ) , popMode
;

M_Interface_POLICY
:
   'policy' -> type ( POLICY ) , popMode
;

M_Interface_L2TRANSPORT
:
   'l2transport' -> type ( L2TRANSPORT ) , popMode
;

M_Interface_MODULE
:
   'module' -> type ( MODULE )
;

M_Interface_MULTIPOINT
:
   'multipoint' -> type ( MULTIPOINT ) , popMode
;

M_Interface_COLON
:
   ':' -> type ( COLON )
;

M_Interface_COMMA
:
   ',' -> type ( COMMA )
;

M_Interface_DASH
:
   '-' -> type ( DASH )
;

M_Interface_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_Interface_NUMBER
:
   DEC -> type ( DEC )
;

M_Interface_PERIOD
:
   '.' -> type ( PERIOD )
;

M_Interface_PRECFONFIGURE
:
   'preconfigure' -> type ( PRECONFIGURE )
;

M_Interface_PREFIX
:
   (
      F_Letter
      (
         F_Letter
         | '-'
         | '_'
      )*
   )
   | 'Dot11Radio'
;

M_Interface_RELAY
:
   'relay' -> type ( RELAY ) , popMode
;

M_Interface_SLASH
:
   '/' -> type ( FORWARD_SLASH )
;

M_Interface_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_ISO_Address;

M_ISO_Address_ISO_ADDRESS
:
   F_HexDigit+
   (
      '.' F_HexDigit+
   )+ -> type ( ISO_ADDRESS ) , popMode
;

M_ISO_Address_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_Key;

M_Key_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_Key_NON_NEWLINE
:
   F_NonNewline+
;

mode M_MOTD_C;

M_MOTD_C_ESCAPE_C
:
   (
      '^C'
      |
      (
         '^' F_Newline
      )
      | 'cC'
      | '\u0003'
   ) -> type ( ESCAPE_C ) , mode ( DEFAULT_MODE )
;

M_MOTD_C_MOTD
:
   (
      (
         '^' ~[^C\u0003\n\r]
      )
      |
      (
         'c' ~[^C\u0003]
      )
      | ~[c^\u0003]
   )+
;

mode M_MOTD_EOF;

M_MOTD_EOF_EOF
:
   'EOF' -> type ( EOF_LITERAL ) , mode ( DEFAULT_MODE )
;

M_MOTD_EOF_MOTD
:
   (
      ~'E'
      |
      (
         'E' ~'O'
      )
      |
      (
         'EO' ~'F'
      )
   )+
;

mode M_MOTD_HASH;

M_MOTD_HASH_HASH
:
   '#' -> type ( POUND ) , mode ( DEFAULT_MODE )
;

M_MOTD_HASH_MOTD
:
   ~'#'+
;

mode M_Name;

M_Name_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

M_Name_NAME
:
   F_NonWhitespace+ -> type ( VARIABLE ) , popMode
;

mode M_NEIGHBOR;

M_NEIGHBOR_CHANGES
:
   'changes' -> type ( CHANGES ) , popMode
;

M_NEIGHBOR_IP_ADDRESS
:
   F_DecByte '.' F_DecByte '.' F_DecByte '.' F_DecByte -> type ( IP_ADDRESS ) ,
   popMode
;

M_NEIGHBOR_IP_PREFIX
:
   F_DecByte '.' F_DecByte '.' F_DecByte '.' F_DecByte '/' F_Digit F_Digit? ->
   type ( IP_PREFIX ) , popMode
;

M_NEIGHBOR_IPV6_ADDRESS
:
   (
      (
         (
            '::'
            (
               (
                  F_HexDigit+ ':'
               )* F_HexDigit+
            )?
         )
         |
         (
            F_HexDigit+ ':' ':'?
         )+
      )
      (
         F_HexDigit+
      )?
   ) -> type ( IPV6_ADDRESS ) , popMode
;

M_NEIGHBOR_IPV6_PREFIX
:
   (
      (
         '::'
         (
            (
               F_HexDigit+ ':'
            )* F_HexDigit+
         )?
      )
      |
      (
         F_HexDigit+ ':' ':'?
      )+
      (
         F_HexDigit+
      )? '/' F_DecByte
   ) -> type ( IPV6_PREFIX ) , popMode
;

M_NEIGHBOR_PASSIVE
:
   'passive' -> type ( PASSIVE ) , popMode
;

M_Neighbor_VARIABLE
:
   F_Variable_VarChar+ -> type ( VARIABLE ) , popMode
;

M_NEIGHBOR_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_NEIGHBOR_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;

mode M_REMARK;

M_REMARK_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

M_REMARK_REMARK
:
   F_NonNewline+
;

mode M_Rule;

M_Rule_LINE
:
   F_NonNewline+
;

M_Rule_NEWLINE
:
   F_Newline+ -> type ( NEWLINE ) , popMode
;

mode M_SHA1;

M_SHA1_DEC_PART
:
   F_Digit+
;

M_SHA1_HEX_PART
:
   F_HexDigit+ -> popMode
;

M_SHA1_WS
:
   F_Whitespace+ -> channel ( HIDDEN )
;
