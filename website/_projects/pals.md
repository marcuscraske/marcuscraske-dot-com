---
layout: post
title: PALS - Programming Assessment and Learning System
thumbnail: /assets/projects/pals/thumb.png
priority: 2014
---

A platform for automated and distributed marking of programming assessments, completed as an undergraduate project.

# Overview
PALS is an [open source](https://github.com/limpygnome/pals) assessment system for programming assignments,
which uses dynamic and static analysis techniques for automated marking. Its architecture allows for multiple nodes to
process work simultaneously, with dynamic analysis executing student code within a sandboxed environment. Plugins can also
be hotswapped and automatically reloaded across all nodes, allowing for extended functionality to be added and changed without
restarting the system.

[Github](https://github.com/limpygnome/pals)

For a more detailed technial explanation of the system, refer to the project report section.

# Features
- Multiple question types:
    - Java code - allows editing, compilation and automated testing of code written in Java. Submission can be through an editor or by uploading a zip file.
    - Written responses - for essays/text answers, can be manually and automatically marked.
    - Multiple choice and response - randomly ordered options, persisted for an instance of an assignment.

- Multiple criterias combined to form the overall mark for a question, which can be given different weightings.
- Multiple criteria types for marking questions:
    - Regex matching.
    - Code metrics - lines of code, blank lines, comment lines, average identifier length (classes, methods or fields).
    - Testing inputs - execute code with a specified or random set of inputs. This is executed against both student and correct code, with the
      outputs compared to determine correctness.
    - Standard input/output testing.
    - Existence of classes, methods, fields or enums (including values).
    - Java inheritance and interfaces.
    - Custom code capable of providing a mark between 0 to 100 for a criteria. Custom feedback can be also provided to the student.

- Security:
    - XSS (cross-site scripting) and CSRF (cross-site request forgery) protection.
    - Custom session management using SHA-256 hashes for session identifiers, using guidelines from OWASP.
    - Captcha protection to prevent automated brute-forcing.
    - Passwords hashed with SHA-512 with unique salts for each password.
    - Secure inter-node communication using RMI with SSL.

- Aggregation of exceptions, warnings and issues found with code. Useful for addressing common mistakes by students.
- Hotswappable plugins architecture, allowing for a plugin to be added/changed from a central file share and (re)loaded in every node.
- Mass enrollment.

# Download
Releases can be found at:

<https://github.com/limpygnome/pals/releases>

# Project Report
<p>
    Click [here](/assets/projects/pals/report.pdf) to download the final project report.
</p>

# Screenshots

<ul class="gallery">
    <li>
        <a href="/assets/projects/pals/criteria_results_inputs.png" class="screenshot">
            <img src="/assets/projects/pals/criteria_results_inputs.png" alt="Criteria results" />
            The results from dynamic assessment of student code and correct code.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/criteria_results_output.png" class="screenshot">
            <img src="/assets/projects/pals/criteria_results_output.png" alt="Standard in out testing" />
            Dynamic testing of standard input/output.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/stats_hint.png" class="screenshot">
            <img src="/assets/projects/pals/stats_hint.png" alt="Feedback hint" />
            Feedback providing the student with a hint of the issue wrong.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/stats.png" class="screenshot">
            <img src="/assets/projects/pals/stats.png" alt="Stats" />
            Stats for common issues.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/testing_graph.png" class="screenshot">
            <img src="/assets/projects/pals/testing_graph.png" alt="Distributed test" />
            Graph of time to mark assessments with 1 to 5 nodes simultaneously.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/erd.png" class="screenshot">
            <img src="/assets/projects/pals/erd.png" alt="ERD" />
            ERD for PALS.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/home.png" class="screenshot">
            <img src="/assets/projects/pals/home.png" alt="Home" />
            The home page.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/nodes.png" class="screenshot">
            <img src="/assets/projects/pals/nodes.png" alt="Nodes" />
            Administration page for nodes.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/plugins.png" class="screenshot">
            <img src="/assets/projects/pals/plugins.png" alt="Plugins" />
            Administration page for plugins.
        </a>
    </li>
    <li>
        <a href="/assets/projects/pals/enrollment.png" class="screenshot">
            <img src="/assets/projects/pals/enrollment.png" alt="Enrollment" />
            Mass enrollment page.
        </a>
    </li>
</ul>
