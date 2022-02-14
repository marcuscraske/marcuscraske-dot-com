# Deployment

- [dns](dns)
  - Used to setup DNS for various domains, building the main site.
- [main-website](main-website)
  - Used to setup and deploy files for main static website.
- [state-bucket](state-bucket)
  - Terraform state and lock is held on AWS, see [state-bucket](state-bucket/README.md).
  - Required for very first build of infrastructure.
