# README

## Introduction

This project provides a full life-cycle support framework for blockchain application development. It adopts a Model-Driven Engineering (MDE) approach to manage the complexity inherent in blockchain systems and to streamline the overall development process. Traditional blockchain development often suffers from long development cycles, high costs, and frequent inconsistencies across stages. To address these issues, we propose a **model-driven full life-cycle support framework** that systematically covers all Software Development Life Cycle (SDLC) phases, from requirements analysis to deployment.

The key contribution of the framework lies in its integration of multiple modeling techniques—such as UML, BPMN, and DAOM—to explicitly capture decentralized roles, interactions, and workflows. Blockchain-specific characteristics, including decentralization, immutability, and multi-party collaboration, significantly complicate system design and implementation. The proposed framework introduces a standardized modeling process and blockchain-aware transformation mechanisms, enabling automated and consistent transitions between SDLC stages and reducing manual effort.

The framework is designed to remain platform-independent at the modeling level while supporting heterogeneous blockchain platforms, including Ethereum and Hyperledger Fabric. Its feasibility and effectiveness have been validated through a real-world **cross-border food supply chain management system**, demonstrating tangible improvements in development efficiency, consistency, and overall practicality.

## Plug-ins

The framework relies on several key plug-ins to support model transformation and code generation throughout the SDLC:

### 1. QVT Plug-in
- **Purpose**: The QVT (Query/View/Transformation) plug-in defines model-to-model transformation rules, enabling systematic conversion between different modeling artifacts.
- **Usage**: It is used to automate transformations across SDLC stages, such as mapping requirement-level models to UML design models, thereby reducing manual intervention and improving transformation correctness.

### 2. Acceleo Plug-in
- **Purpose**: Acceleo supports model-to-code generation through template-based transformations.
- **Usage**: In this project, Acceleo is primarily used to generate smart contract code and deployment scripts from UML models, improving automation and ensuring consistency between models and executable artifacts.

### 3. UMLplanet Plug-in
- **Purpose**: UMLplanet provides visualization and editing support for UML models, including class and sequence diagrams.
- **Usage**: It is used to construct and inspect the structural and behavioral models of blockchain systems, helping developers better understand system design and validate modeling decisions.

## Project Environment

The project was developed and evaluated in the following environment:

| Environment           | Description                                      |
|-----------------------|--------------------------------------------------|
| **Operating System**  | Windows 10                                       |
| **Machine**           | Lenovo G40 75m                                   |
| **Processor**         | AMD A10-7300 Radeon R6 (4C+6G), 1.90 GHz          |
| **Memory (RAM)**      | 8 GB                                             |
| **Runtime Platform**  | Eclipse Mars 2                                   |
| **Development Kit**   | Java SE 1.8                                      |
| **Modeling Tool**     | Papyrus 1.1.4                                    |

### Environment Details

1. **Operating System and Hardware**  
   The framework was developed on Windows 10 using a Lenovo G40 75m laptop. This configuration supports medium-scale modeling and code generation tasks. The AMD A10-7300 processor and 8 GB of RAM provide sufficient computational resources for model processing and transformation workflows.

2. **Development Platform**  
   Eclipse Mars 2 serves as the primary development environment, offering mature support for MDE-related plug-ins. Java SE 1.8 provides a stable foundation for implementing transformation logic and integrating with blockchain platforms.

3. **Modeling and Transformation Tools**  
   - **Papyrus 1.1.4** is used for creating and managing UML models.
   - **Acceleo 3.6** enables automated generation of code artifacts from models.
   - **QVT 3.5** supports the definition and execution of model transformation rules, ensuring consistency across SDLC stages.
