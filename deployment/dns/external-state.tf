# State read from another state bucket i.e. outputs from main-website
data "terraform_remote_state" "main-website" {
    backend = "s3"
    config = {
        bucket = "tfstate-marcuscraske-dot-com"
        key    = "terraform.tfstate"
        region = "eu-west-1"
    }
}

data "terraform_remote_state" "public-files" {
    backend = "s3"
    config = {
        bucket = "tfstate-marcuscraske-dot-com"
        key    = "public-files.terraform.tfstate"
        region = "eu-west-1"
    }
}
