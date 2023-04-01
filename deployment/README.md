# Deployment

- [dns](dns)
  - Used to setup DNS for various domains, building the main site, using Cloudflare and Terraform.
- [dns-redirect](dns-redirect)
  - Terraform module, used by the previously mentioned _dns_ directory, for boilerplate Cloudflare redirects.
- [state-bucket](state-bucket)
  - Terraform state and lock is held on AWS, see [state-bucket](state-bucket/README.md).
  - Required for very first build of infrastructure.
