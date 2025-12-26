# Requirements Specification — Blockchain SDLC MDD Prototype

> **Purpose**  
> This document specifies the requirements used in the case study/experiment reported in the paper.  
> It is designed to be **auditable** and **traceable** from requirements → models → generated artifacts → tests → deployment.



## 1. Background and Objectives

### 1.1 Context
This case targets a blockchain-based traceability system for cross-border seafood supply chains. Multiple stakeholders collaborate to record, verify, and query lifecycle events of seafood batches (e.g., catch records, processing updates, cold-chain handovers, retail availability, and consumer verification).

### 1.2 Objectives
- **O1 Traceability:** enable end-to-end traceability of seafood batches across organizations.
- **O2 Integrity & Auditability:** ensure records are tamper-evident and auditable.
- **O3 Controlled Access:** enforce role-based write/read permissions.
- **O4 Automation:** support model-driven generation of blockchain-specific artifacts across SDLC phases.

### 1.3 Scope Statement (In/Out)
**In scope**
- Core batch lifecycle recording and handover tracking
- Core access control and auditable event logging
- Generation of smart contracts, tests, and deployment scripts from models

**Out of scope**
- Full UI/UX implementation
- Full-scale production operations and SLA guarantees
- Non-essential business processes not used in the experiment

---

## 2. System Boundary and Assumptions

### 2.1 On-chain vs Off-chain Boundary
| Data Type | Stored On-chain? | Stored Off-chain? | Rationale |
|---|---:|---:|---|
| Batch identifiers, lifecycle states, key timestamps | ✅ | ⛔ | Auditable state transitions |
| Large documents (certificates, photos, full logs) | ⛔(Only Hash) | ✅ | Size/cost constraints |
| Off-chain metadata hashes (integrity anchors) | ✅ | ✅ | On-chain anchoring of off-chain content |

### 2.2 Trust & Governance Assumptions
- Participants are known organizations (permissioned or semi-permissioned context).
- Each organization controls its identity credentials/keys.
- The system supports audit queries by permitted parties.

### 2.3 Blockchain Constraints Considered
- Immutability (post-deployment patching is constrained)
- Platform heterogeneity (e.g., Ethereum vs Fabric)
- Event-based observability for auditing and testing

---

## 3. Stakeholders and Roles

### 3.1 Stakeholder List (Canonical Names)
| Role ID | Role | Abbreviation | Description |
|---|---|---|---|
| R-01 | Raw food ingredient provider | RawFoodMaker | Records catch/raw material creation |
| R-02 | Food manufacturer | Manufacturer | Records processing/packaging updates |
| R-03 | Wholesaler | Wholesaler | Records distribution and handovers |
| R-04 | Logistics provider | Logistics | Records shipment, handover, cold-chain evidence |
| R-05 | Retailer | Retailer | Records receiving and retail availability |
| R-06 | Consumer | Consumer | Queries and verifies authenticity |
| R-07 | (Optional) Regulator/Customs | Regulator | Performs compliance/audit checks |

### 3.2 Role Responsibilities (Summary)
- RawFoodMaker: create batch; submit catch attributes; anchor metadata hash.
- Manufacturer: confirm receipt; record processing steps; update batch status.
- Logistics: record shipment creation; handover events; temperature evidence hash.
- Retailer: confirm receipt; mark for sale; provide consumer query endpoint.
- Consumer: read-only verification queries.

### 3.3 Access Control Principles (High-level)
- Write operations restricted by role ownership and lifecycle state.
- Read operations: public for non-sensitive fields, restricted for sensitive fields.
- All state changes must be auditable (event logs + state storage).

---

## 4. Domain Concepts and Data Requirements

### 4.1 Core Entities (Minimal Set for Reproduction)

#### 4.1.1 Seafood Batch (`Batch`)
| Field | Type | Required | Description |
|---|---|---:|---|
| batchId | string/uint | ✅ | Unique identifier |
| origin | string | ✅ | Origin/catch location |
| catchTime | timestamp | ✅ | Catch time |
| weight | number | ✅ | Weight |
| ownerRole | enum | ✅ | Current owner role |
| status | enum | ✅ | Lifecycle status |
| metadataHash | bytes/string | ✅ | Off-chain metadata anchor |
| lastUpdated | timestamp | ✅ | Last update time |

#### 4.1.2 Handover Record (`Handover`)
| Field | Type | Required | Description |
|---|---|---:|---|
| handoverId | string/uint | ✅ | Unique identifier |
| batchId | string/uint | ✅ | Linked batch |
| fromRole | enum | ✅ | Sender role |
| toRole | enum | ✅ | Receiver role |
| time | timestamp | ✅ | Transfer time |
| evidenceHash | bytes/string | ⛔/✅ | Optional evidence |

#### 4.1.3 Shipment / Cold-chain Evidence (`Shipment`)
| Field | Type | Required | Description |
|---|---|---:|---|
| shipmentId | string/uint | ✅ | Shipment identifier |
| batchId | string/uint | ✅ | Linked batch |
| startTime/endTime | timestamp | ✅ | Shipment window |
| tempEvidenceHash | bytes/string | ⛔/✅ | Anchor for off-chain logs |

### 4.2 Lifecycle States (Canonical)
Define minimal lifecycle states used in the experiment:
- `CREATED` (by RawFoodMaker)
- `PROCESSED` (by Manufacturer)
- `IN_TRANSIT` (by Logistics)
- `RECEIVED` (by Retailer)
- `AVAILABLE` (for consumer verification)

> If your paper uses a different state set, list the exact states used and keep consistent across DAOM/BPMN/UML/code/tests.

---

## 5. Business Processes and Scenarios 

> Provide each scenario with: **Trigger → Pre-conditions → Inputs → Steps → Outputs → Post-conditions**.  
> Each scenario must map to BPMN process fragments and generated artifacts.

### S-01 Record Catch (Batch Creation)
- **Primary Actor:** RawFoodMaker  
- **Trigger:** New batch/catch recorded  
- **Pre-conditions:** RawFoodMaker is registered and authorized  
- **Inputs:** `batchId`, `origin`, `catchTime`, `weight`, `metadataHash`  
- **Steps:**  
  1) Create batch record  
  2) Set initial status `CREATED`  
  3) Emit auditable event  
- **Outputs:** Batch exists and is queryable  
- **Post-conditions:** `ownerRole = RawFoodMaker`, `status = CREATED`

### S-02 Transfer to Manufacturer (Handover + Receipt Confirmation)
- **Primary Actors:** RawFoodMaker → Manufacturer  
- **Pre-conditions:** batch `status = CREATED`  
- **Inputs:** `batchId`, `handoverId`, optional `evidenceHash`  
- **Outputs:** Handover recorded; ownership updated upon acceptance  
- **Post-conditions:** `ownerRole = Manufacturer`, status updated (e.g., `PROCESSED` or `RECEIVED` depending on your design)

### S-03 Manufacturing Update (Processing/Packaging)
- **Primary Actor:** Manufacturer  
- **Pre-conditions:** Manufacturer owns the batch  
- **Inputs:** processing info hash, timestamp  
- **Outputs:** batch status updated; event emitted  
- **Post-conditions:** `status = PROCESSED`, metadata anchored

### S-04 Logistics Shipment & Cold-chain Evidence Anchoring
- **Primary Actor:** Logistics  
- **Pre-conditions:** batch handed over to Logistics / authorized shipment creation  
- **Inputs:** shipment window, `tempEvidenceHash`  
- **Outputs:** shipment record stored/anchored; event emitted  
- **Post-conditions:** `status = IN_TRANSIT`

### S-05 Retail Receiving & Consumer Verification
- **Primary Actors:** Retailer, Consumer  
- Retailer: confirm receiving; mark `AVAILABLE`  
- Consumer: query batch history and verify integrity (state + anchored hashes)

---

## 6. Functional Requirements (FR)

> **Rules:**  
> - Each requirement must have a unique ID (FR-xx).  
> - Each FR must specify **acceptance criteria** (testable).  
> - Each FR must link to **model elements** and **generated artifacts** in Section 9.

### FR List

#### FR-01 Batch Creation
- **Description:** The system shall allow RawFoodMaker to create a new Batch record with required attributes and metadata hash.
- **Acceptance Criteria:**  
  - AC-01.1: A valid create request persists batch state with `status = CREATED`.  
  - AC-01.2: An event/log indicating successful creation is emitted.  
  - AC-01.3: Unauthorized roles cannot create batch.

#### FR-02 State Transition on Processing
- **Description:** Manufacturer shall update batch status and anchor processing metadata.
- **Acceptance Criteria:**  
  - AC-02.1: Only Manufacturer can perform processing update when ownerRole = Manufacturer.  
  - AC-02.2: Status becomes `PROCESSED`; update is auditable (event/log).  

#### FR-03 Handover Recording
- **Description:** The system shall record handover between roles and maintain auditable transfer history.
- **Acceptance Criteria:**  
  - AC-03.1: Handover links to batch and records from/to roles.  
  - AC-03.2: Ownership updates only after receiver confirmation (if applicable).  

#### FR-04 Shipment & Cold-chain Evidence Anchoring
- **Description:** Logistics shall record shipment and anchor temperature evidence hash.
- **Acceptance Criteria:**  
  - AC-04.1: Shipment record is created with correct window.  
  - AC-04.2: Evidence hash is stored/anchored and queryable.  

#### FR-05 Retail Availability Update
- **Description:** Retailer shall mark a batch as available for consumer verification.
- **Acceptance Criteria:**  
  - AC-05.1: Only Retailer can mark available after receiving.  
  - AC-05.2: Status becomes `AVAILABLE`; event/log emitted.  

#### FR-06 Traceability Query
- **Description:** The system shall provide query functions to retrieve batch state and lifecycle history.
- **Acceptance Criteria:**  
  - AC-06.1: Query returns current status, ownerRole, and key timestamps.  
  - AC-06.2: Query returns list of handovers/shipments (or pointers) if enabled.  

#### FR-07 Access Control Enforcement
- **Description:** The system shall enforce role-based access control for write operations and sensitive reads.
- **Acceptance Criteria:**  
  - AC-07.1: Unauthorized writes are rejected.  
  - AC-07.2: Read permissions follow policy in Section 3.3.  

---

## 7. Non-Functional Requirements (NFR)

> Quantify where possible or specify a verifiable criterion.

#### NFR-01 Correctness & Safety
- The system shall prevent invalid state transitions (e.g., cannot mark AVAILABLE before RECEIVED).
- Verifiable via negative tests and invariant checks.

#### NFR-02 Auditability
- All state-changing operations shall produce auditable records (state + event/log).
- Verifiable by event/log presence and queryable history.

#### NFR-03 Performance (Experiment Scope)
- Define the performance context used in evaluation (e.g., concurrency, query rate).
- Record the exact workload configuration used for reproduction (see Section 10.3).

#### NFR-04 Maintainability (MDD Continuity)
- Model changes should propagate to regenerated artifacts with minimal manual edits.
- Record manual edits as part of reproducibility logs (Section 11).

#### NFR-05 Privacy (If Applicable)
- Specify which fields are restricted and how off-chain storage is used.
- Provide explicit access policy statements.

---

## 8. Blockchain-Specific Requirements

### BCR-01 Immutability-Aware Design
- Contracts/artifacts must consider post-deployment immutability constraints.
- Any upgrade/migration assumptions must be documented.

### BCR-02 Observability for Verification
- State-changing operations must be observable for testing/auditing (state + event/log, depending on platform).

### BCR-03 Multi-Platform Generation (If Applicable)
- The framework shall generate platform-specific artifacts from platform-agnostic models (document supported targets and mappings).

---


## 0. Document Control

| Field | Value |
|---|---|
| Project | Blockchain SDLC MDD Prototype |
| Case | Cross-border seafood supply-chain traceability (catch → processing → logistics → retail → consumer verification) |
| Version | v1.0 |
| Authors | Yanze Wang |
| Repository | [\<GitHub URL\>](https://github.com/wyz426/mdd_blockchain_sdlc_framework) |
| License | \<Apache-2.0\> |
| Contact | \<email\> |

---
