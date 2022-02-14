data "terraform_remote_state" "main-website" {
    backend = "s3"
    config = {
        bucket = "tfstate-marcuscraske-dot-com"
        key    = "terraform.tfstate"
        region = "eu-west-1"
    }
}
